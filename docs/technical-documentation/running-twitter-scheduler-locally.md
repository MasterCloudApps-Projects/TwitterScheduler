# Running Twitter Scheduler locally

A docker compose file is provided in order to provide the capability of execute Twitter Scheduler locally, allowing to publish tweets in your own Twitter account.

## Prerequesites

Is needed to have docker and docker compose installed.

## Twitter API credentials

Log in your Twitter account and get your Twitter API credentials: [https://developer.twitter.com/en/portal/dashboard](https://developer.twitter.com/en/portal/dashboard)

Add them to [docker-compose.yml](../../docker-compose/docker-compose.yml) file:

```
    environment:
      - TWITTER_CONSUMER_KEY=<your-twitter-consumer-key>
      - TWITTER_CONSUMER_SECRET=<your-twitter-consumer-secret>
      - TWITTER_ACCESS_TOKEN=<your-twitter-access-token>
      - TWITTER_ACCESS_TOKEN_SECRET=<your-twitter-access-token-secret>
```

## Run Twitter Scheduler

From repository root path:

```
cd docker-compose
docker-compose up
```

Application is deployed: [http://localhost:8080/](http://localhost:8080/)

- [OpenAPI](http://localhost:8080/openapi.html)
- [Feature Toggles](http://localhost:8080/togglz)
- [App info](http://localhost:8080/actuator/info)
- [App health](http://localhost:8080/actuator/health)

## Stop Twitter Scheduler

```
docker-compose down
```


