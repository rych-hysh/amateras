# Amateras

Amateras is a app showing forex rate chart. Alert via LINE Message API when you should buy or sell in the future.

# Build
Make sure `spring.datasource.url` at application-{env}.yml is
```yml
spring.datasource.url: jdbc:mysql://localhost:3306/amateras_db?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true
```
at application-{env}.yml before build. Then run 
```bash
./gradlew build
```

to build. 

# Run
## On Windows
1. Start MySQL server
   ```bash
   docker-compose up -d db
   ```
   to start mysql server on docker.
2. Run

   Make sure `spring.datasource.url` at application-{env}.yml is
   ```yml
   spring.datasource.url: jdbc:mysql://localhost:3306/amateras_db?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true
   ```
   at application-{env}.yml before run. Then
   ```bash
   ./gradlew bootrun --args='--spring.profiles.active=dev'
   ```
   to run.
## On docker
Before run on docker, make sure that
```yml
spring.datasource.url: jdbc:mysql://db:3306/amateras_db?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true
```
at application-{env}.yml.
#### BootRun
To bootrun spring app and others on docker, run
```bash
$env:IS_BUILD_MODE="false" ; docker-compose up -d
```
#### Build and Run
To build and run spring app on docker and run others on docker, run
```bash
$env:IS_BUILD_MODE="true" ; docker-compose up -d
```

# Debug

## Run spring app on windows ( others on docker )

1. Start MySQL server
    ```bash
    docker-compose up -d db
    ```
    to start mysql server on docker.

2. Start debug
         
   Before debugging, make sure that
      ```yml
      spring.datasource.url: jdbc:mysql://localhost:3306/amateras_db?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true
      ```
   at application-{env}.yml.
      
   Then press SHIFT + F9 to start debugging. 
   

# ~~Jenkins~~(currently unavailable)

github (push event)-> jenkins (ci/cd) -> server (deploy)

1. Push to develop.
2. Webhook to jenkins.
3. Deploy to server (aws ec2).