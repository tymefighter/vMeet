# vMeet Backend Application

## Development

### Requirements

The requirements for development are as follows:

1. `docker` for running the data dependencies like Mongo, Redis and ElasticSearch
2. An IDE for development using `Java`. 

### Running

Firstly, run the docker compose file for development data dependencies:

```bash
docker-compose -f docker-compose.development-data.yml up
```

Finally, you can start the application using `gradle`:

```bash
MONGO_HOST=127.0.0.1 REDIS_HOST=127.0.0.1 ./gradlew :application:bootRun 
```

## Production

### Requirements

The only requirements for running the production build locally is `docker`.

### Running

Just run the following command in the application root directory, i.e. `vmeet-app`:

```bash
docker-compose up --build
```
