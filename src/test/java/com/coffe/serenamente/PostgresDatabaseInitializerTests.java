package com.coffe.serenamente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PostgresDatabaseInitializerTests {
    private static final String URL = "jdbc:postgresql://localhost:5432/serenamente?sslmode=require";
    private static final String ADMIN_URL = "jdbc:postgresql://localhost:5432/postgres?sslmode=require";

    @Test
    void existingDatabaseDoesNotRequireAdministrativeConnection() throws SQLException {
        try (var driver = mockStatic(DriverManager.class)) {
            var connection = mock(Connection.class);
            driver.when(() -> DriverManager.getConnection(URL, "user", "password")).thenReturn(connection);
            PostgresDatabaseInitializer.ensureDatabaseExists(URL, "user", "password");
            driver.verify(() -> DriverManager.getConnection(ADMIN_URL, "user", "password"), never());
            verify(connection).close();
        }
    }

    @Test
    void missingDatabaseIsCreatedAndConnectionOptionsArePreserved() throws SQLException {
        try (var driver = mockStatic(DriverManager.class)) {
            var connection = mock(Connection.class);
            var statement = mock(Statement.class);
            driver.when(() -> DriverManager.getConnection(URL, "user", "password"))
                    .thenThrow(new SQLException("missing", "3D000"));
            driver.when(() -> DriverManager.getConnection(ADMIN_URL, "user", "password")).thenReturn(connection);
            when(connection.createStatement()).thenReturn(statement);
            PostgresDatabaseInitializer.ensureDatabaseExists(URL, "user", "password");
            verify(statement).executeUpdate("CREATE DATABASE \"serenamente\"");
            verify(statement).close();
            verify(connection).close();
        }
    }

    @Test
    void authenticationFailureIsPreserved() {
        try (var driver = mockStatic(DriverManager.class)) {
            var failure = new SQLException("authentication failed", "28P01");
            driver.when(() -> DriverManager.getConnection(URL, "user", "password")).thenThrow(failure);
            assertSame(failure, assertThrows(SQLException.class,
                    () -> PostgresDatabaseInitializer.ensureDatabaseExists(URL, "user", "password")));
            driver.verify(() -> DriverManager.getConnection(ADMIN_URL, "user", "password"), never());
        }
    }

    @Test
    void concurrentCreationIsAcceptedAndIdentifiersAreEscaped() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/a%22b";
        try (var driver = mockStatic(DriverManager.class)) {
            var connection = mock(Connection.class);
            var statement = mock(Statement.class);
            driver.when(() -> DriverManager.getConnection(url, "user", "password"))
                    .thenThrow(new SQLException("missing", "3D000"));
            driver.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "user", "password"))
                    .thenReturn(connection);
            when(connection.createStatement()).thenReturn(statement);
            when(statement.executeUpdate("CREATE DATABASE \"a\"\"b\""))
                    .thenThrow(new SQLException("already created", "42P04"));
            PostgresDatabaseInitializer.ensureDatabaseExists(url, "user", "password");
            verify(statement).executeUpdate("CREATE DATABASE \"a\"\"b\"");
        }
    }
}
