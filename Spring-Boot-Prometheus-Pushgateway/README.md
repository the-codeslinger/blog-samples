# Spring Boot Prometheus Pushgateway

This sample project demonstrates how to configure Prometheus Pushgateway for 
short-lived tasks where Prometheus scraping is not possible.

See the accompanying [blog post][blog] for explanations to the code.

Run a Pushgateway for local testing in Docker.
```shell
docker run -d -p 9091:9091 --name=prometheus-pushgateway prom/pushgateway:latest
```

[blog]: http://thecodeslinger.org/2022/10/30/spring-boot-push-micrometer-metrics-to-prometheus-pushgateway/