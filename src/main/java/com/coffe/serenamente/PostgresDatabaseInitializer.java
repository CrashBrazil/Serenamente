package com.coffe.serenamente;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class PostgresDatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        var environment = context.getEnvironment();
        if (!environment.getProperty("app.database.create-if-missing", Boolean.class, true)) {
            return;
        }
        String url = environment.getRequiredProperty("spring.datasource.url");
        if (!url.startsWith("jdbc:postgresql://")) {
            return;
        }
        String username = environment.getRequiredProperty("spring.datasource.username");
        String password = environment.getRequiredProperty("spring.datasource.password");
        try {
            ensureDatabaseExists(url, username, password);
        } catch (SQLException exception) {
            throw new IllegalStateException("Não foi possível preparar o banco PostgreSQL. "
                    + "Se o banco não existir, o usuário precisa da permissão CREATEDB "
                    + "e de acesso ao banco administrativo postgres.", exception);
        }
    }

    static void ensureDatabaseExists(String url, String username, String password) throws SQLException {
        try (var connection = DriverManager.getConnection(url, username, password)) {
            return;
        } catch (SQLException exception) {
            if (!"3D000".equals(exception.getSQLState())) {
                throw exception;
            }
        }

        int queryStart = url.indexOf('?');
        String baseUrl = queryStart < 0 ? url : url.substring(0, queryStart);
        String query = queryStart < 0 ? "" : url.substring(queryStart);
        int databaseStart = baseUrl.lastIndexOf('/');
        String database = URLDecoder.decode(baseUrl.substring(databaseStart + 1).replace("+", "%2B"),
                StandardCharsets.UTF_8);
        if (database.isBlank()) {
            throw new IllegalArgumentException("Informe o nome do banco na URL JDBC PostgreSQL.");
        }
        String adminUrl = baseUrl.substring(0, databaseStart + 1) + "postgres" + query;
        try (var connection = DriverManager.getConnection(adminUrl, username, password);
                var statement = connection.createStatement()) {
            try {
                statement.executeUpdate("CREATE DATABASE \"" + database.replace("\"", "\"\"") + "\"");
            } catch (SQLException exception) {
                // Another application instance may have created the database meanwhile.
                if (!"42P04".equals(exception.getSQLState())) {
                    throw exception;
                }
            }
        }
    }
}
