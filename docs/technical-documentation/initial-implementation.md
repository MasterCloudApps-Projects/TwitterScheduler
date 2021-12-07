# Initial implementation

- [Description](#description)
- [Domain](#domain)
- [Database](#database)
- [REST API](#rest-api)

---

## Description

Twitter Scheduler provides the capacity to schedule the publication of tweets in Twitter social network.

It allows to create pending tweets with a publication date and a message (Tweet content).

There is a scheduled task, executed every three minutes, that checks if there is any pending tweet with a publication date before that current time (UTC) plus three minutes (in order to cover up to next scheduled execution). If there are tweets that match with these requirements, it will be published.

The scheduler can be disabled in `/togglz` application path (credentials needed)


## Domain

![initial-domain](../images/initial-implementation/domain-initial.png)

## Database

![initial-database](../images/initial-implementation/db-initial.png)

## REST API

[OpenAPI specification](https://swagger.io/specification/) has been used to document provided REST API:

![openapi](../images/initial-implementation/openapi.png)

Example of pending tweet creation request:

```
{
  "message": "This is a test tweet content",
  "publicationDate": "2022-04-10T10:00:00Z"
}
```