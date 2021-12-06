[![License badge](https://img.shields.io/badge/license-Apache2-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)
![push main workflow](https://github.com/MasterCloudApps-Projects/TwitterScheduler/actions/workflows/push-main.yml/badge.svg)

![][TwitterScheduler Logo]

# Twitter Scheduler

This project was born as a Master's Dissertation, it is based in the implementation of an application using Trunk-based development with two new features applying Feature toggles and Branch by abstraction design techniques.

Application has been implemented with Java 11 using Spring Boot and following a hexagonal architecture applying Domain Driven Design, it provides the capacity to schedule the publication of tweets in [Twitter](https://twitter.com/) social network in this account: [BlueOcean_TFM](https://twitter.com/BlueOcean_TFM)

On every commit, a GitHub actions workflow is executed, running unitary and integration tests, publishing the docker image in [DockerHub repository](https://hub.docker.com/repository/docker/drojo/twitter-scheduler-tfm) and deploying the application in [Heroku](https://twitter-scheduler-tfm.herokuapp.com/) (credentials needed), also a pair of smoke tests are executed in order to confirm that the application has been successfully deployed.

![][TwitterScheduler App]

Sections:

- [Continuous integration/Continuous Deployment](docs/technical-documentation/ci-cd.md)
- [Initial implementation](docs/technical-documentation/initial-implementation.md)
- Features:
  - [Publish on demand](docs/technical-documentation/features/feature-publish-on-demand.md)
  - [Tweets with images](docs/technical-documentation/features/feature-tweets-with-images.md)

Following additional resources are available:

* 📖 Essay (Spanish)
* 🖼 Slides (Spanish)

## Contact

Twitter: [@davidrojoa](https://twitter.com/davidrojoa)

[TwitterScheduler Logo]: docs/images/twitter-scheduler-logo.png
[TwitterScheduler App]: docs/images/twitter-scheduler-pro.png