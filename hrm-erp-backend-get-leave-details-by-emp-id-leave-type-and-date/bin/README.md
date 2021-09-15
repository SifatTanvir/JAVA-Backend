# HRM-ERP-BACKEND

## Dependency
– If you want to use PostgreSQL:
```xml
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>
```
## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`
- For PostgreSQL:
```
spring.datasource.url= jdbc:postgresql://localhost:5432/testdb
spring.datasource.username= postgres
spring.datasource.password= root

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation= true
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect
```

# Hibernate ddl auto (create, create-drop, validate, update)
```
spring.jpa.hibernate.ddl-auto= update
```
# App Properties
```
nsl.erp.jwtSecret= SecretKey
nsl.erp.jwtExpirationMs = 86400000

```
## Run Spring Boot application
```
mvn spring-boot:run
```
##Follow the below steps
```
1. First install postgresql 
2. Postgesql  default usename is : postgres
3. Postgresql database password should be : root
4. Need to create a database named "testdb3" 
5. Change database postgres to testdb3
6. Run the code, that will create all tables in testdb database.
8. Then signin from postman as an admin using username "nsl@nsl.com" and password "12345678"
```