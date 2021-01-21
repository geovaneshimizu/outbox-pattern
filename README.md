# Outbox pattern

Sample implementation of the [Transactional Outbox](https://microservices.io/patterns/data/transactional-outbox.html) pattern.

## Running

### Infrastructure dependencies

- AWS SNS and SQS
- PostgreSQL 12.5

```bash
docker-compose up
```

### Applications

- order-service

```bash
cd order-service
docker build -t order-service .
docker run --rm -it --network host order-service
```

- subscription-service

```bash
cd subscription-service
docker build -t subscription-service .
docker run --rm -it --network host subscription-service
```
