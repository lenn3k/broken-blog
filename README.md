# broken-blog
Unsecured blog/forum application in Springboot
# API
Basic endpoints for api/v1/topics and api/v1/posts
Some documentation of the api can be found under /swagger-ui.html



## Step 1 Run KeyCloak

In the ./src/main/docker folder run the following command

`$ docker-compose -f keycloak.yml up`

Point your browser to `http://localhost:9080` and login as 'admin/admin'

You'll see that there are two realms configured (master and jhipster) because we mounted the realm-config folder into our Docker container.

## Sources

* [http://www.baeldung.com/spring-boot-keycloak](http://www.baeldung.com/spring-boot-keycloak)