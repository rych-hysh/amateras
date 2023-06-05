# Amateras

Amateras is a app showing forex rate chart. Alert via LINE Message API when you shoud buy / sell in the future.

# Build

Run 
```bash
./gradlew build
```

to build. 

# Run

Run 

```bash
docker-compose up
```

to build and run app.

# Debug

1. Start MySQL server
    ```bash
    docker-compose up -d db
    ```
    to start mysql server on docker.

2. Start debug
    ```bash
    ./gradlew bootrun
    ```
    or SHIFT + F9 to start debugging.   
# Jenkins

github (push event)-> jenkins (ci/cd) -> server (deploy)

1. Push to develop.
2. Webhook to jenkins.
3. Deploy to server (aws ec2).