# GP Solutions test task

## Implemented features
- [x] Main requirements
- [x] Swagger (http://localhost:8092/swagger-ui.html)
- [x] Unit / Integration tests
- [x] Layered architecture
- [x] Changing SQL dialect without changing code, only spring properties (but for MongoDD it's required)
## Additional
The project contains manually created [OpenAPI specification](api/openapi.yaml), that was used in Apidog for manual API testing
## Requirements
In case of docker compose usage to setup PostgreSQL it's need to provide 
appropriate environment variables. But by default in-memory H2 is used as main DB, so it's optional
## Build
```shell
./mvnw package -DskipTests=true
```
## Test
```shell
./mvnw verify
```
## Run
```shell
./mvnw spring-boot:run
```