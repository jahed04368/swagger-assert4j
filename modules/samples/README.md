# Swagger Assert4j Samples

This module contains sample submodules showing to create/execute test recipes with [Groovy](groovy), 
[Java](java) or the [Maven plugin](maven-plugin).

Execute the Java or Groovy samples by running the following command in their respective root folder:

```
mvn test -PRunSamples
```

Some of the tests require remote execution since they use TestServer-specific features, by default
they will be executed on the public TestServer instance at [http://testserver.readyapi.io/]. You 
can override the TestServer endpoint with:

```
mvn test -PRunSamples -Dtestserver.endpoint=... -Dtestserver.user=... -Dtestserver.password=...
```

The RunSamples profile is defined in the [samples pom](pom.xml) - by default samples are disabled during 
the project build since some of them deliberately fail.