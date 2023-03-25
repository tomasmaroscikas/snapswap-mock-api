# SnapSwap Mock Application

This is a standalone application which emulates SnapSwap RKYC API. Diagram below presents high level interaction between Portal and this app.

![Interaction between Portal and this App](doc/PayFac.SnapSwap.RKYC.Mock.svg "Interaction between Poratl and App")

## How to get started?

In io.wallee.payment.facilitator.snapswap project
- Change URL in SnapSwapEnvironment.java for DEVELOPMENT environment to https://localhost:8443/snapswap/mock
- Start the project

In SnapSwap mock project
- Start application
```./gradlew run```

## How to use?

- Do PayFac onboarding as usual
- Use [Postman Collection](doc/PayFac.SnapSwap.RKYC.API_with_mocking_endpoints.postman_collection.json) to simulate responses from SnapSwap
- Use [this link](https://localhost:8443/snapswap/mock/api/v1/dossier/list) to inspect SnapSwap state

## Technical details

### Micronaut 3.7.3 Documentation

- [User Guide](https://docs.micronaut.io/3.7.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.7.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.7.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
### Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)


