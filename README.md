# Serenamente

## PostgreSQL

Inicie o servidor PostgreSQL e configure as variáveis de ambiente:

```bash
export DB_URL='jdbc:postgresql://localhost:5432/serenamente'
export DB_USERNAME='postgres'
export DB_PASSWORD='sua-senha'
./mvnw spring-boot:run
```

`DB_URL` e `DB_USERNAME` usam os valores acima por padrão. `DB_PASSWORD` é obrigatória.
Antes de inicializar o Hibernate, a aplicação cria o banco indicado em `DB_URL`
caso ele não exista. O usuário configurado deve ter acesso ao banco e, para
criá-lo, permissão `CREATEDB` e acesso ao banco administrativo `postgres` no
mesmo servidor. Bancos existentes são preservados. Erros de autenticação ou de
conexão continuam sendo reportados. Para desativar a criação automática, defina
`DB_CREATE_IF_MISSING=false`.

O Hibernate não altera o esquema por padrão (`DB_DDL_AUTO=none`). Para criar ou
atualizar as tabelas durante o desenvolvimento, defina `DB_DDL_AUTO=update` antes
de iniciar a aplicação. Em produção, mantenha as alterações de esquema sob
controle de migrações.

O teste de inicialização (`./mvnw test`) também precisa de um PostgreSQL acessível
e das mesmas variáveis de ambiente. Em um banco de testes novo, configure
`DB_DDL_AUTO=update` para criar as tabelas.
