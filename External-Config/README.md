Externalize Spring Boot Command Line Project Configuration
==========================================================

**Example 1:** Use internal config

* Do not specify any external config location; solely relies on bundled config.

```shell
% java -jar target/external-config-1.0.0.jar                                                                                

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.2)

-> AppRunner.run() Command Line Arguments
-> ExternalConfigProperties
Input path: /home/the/code
Input path: /home/slinger
```

**Example 2:** Use external profile-specific config in folder without setting profile

* Uses JVM parameters `-D`.
* Specifies a folder; config filename must be `application.yml` or `application-<env>.yml`.
* No active profile is set; searches for `application.yml` and ignores environment-specific config.
* Because no external config is found, only bundled config is used.

```shell
% java -Dspring.config.additional-location=src/config/ -jar target/external-config-1.0.0.jar param1 param2

.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::                (v2.6.2)

-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: param2
-> ExternalConfigProperties
Input path: /home/the/code
Input path: /home/slinger
```

**Example 3:** Use external profile-specific config in folder with setting profile

* Uses JVM parameters `-D`.
* Specifies a folder; config filename must be `application.yml` or `application-<env>.yml`.
* Set active profile to find `application-mac.yml`.
* Augment bundled config values with `spring.config.additional-location`.

```shell
% java -Dspring.config.additional-location=src/config/ -Dspring.profiles.active=mac -jar target/external-config-1.0.0.jar param1 param2

.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::                (v2.6.2)

-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: param2
-> ExternalConfigProperties
Input path: /Users/the/code
Input path: /Users/slinger
```

**Example 4:** Use external profile-specific config in folder with setting profile

* Uses application parameters instead of JVM parameters.
* Specifies a folder; config filename must be `application.yml` or `application-<env>.yml`.
* Set active profile to find `application-mac.yml`.
* Completely ignore bundled config with `spring.config.location`.

```shell
% java -jar target/external-config-1.0.0.jar param1 param2 --spring.profiles.active=mac --spring.config.location=src/config/
2022-01-11 21:13:12.151  INFO 12556 --- [           main] c.t.e.ExternalConfigApplication          : Starting ExternalConfigApplication v1.0.0 using Java 17.0.1 on Roberts-Air.fritz.box with PID 12556 (the-codeslinger.com-blog-samples/External-Config/target/external-config-1.0.0.jar started by rlo in the-codeslinger.com-blog-samples/External-Config)
2022-01-11 21:13:12.152  INFO 12556 --- [           main] c.t.e.ExternalConfigApplication          : The following profiles are active: mac
2022-01-11 21:13:12.343  INFO 12556 --- [           main] c.t.e.ExternalConfigApplication          : Started ExternalConfigApplication in 0.36 seconds (JVM running for 0.529)
-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: param2
Argument: --spring.profiles.active=mac
Argument: --spring.config.location=src/config/
-> ExternalConfigProperties
Input path: /Users/the/code
Input path: /Users/slinger
```

**Example 5:** Use explicitly specified file, regardless of name or active profile

* Uses application parameters instead of JVM parameters.
* Specifies a specific file; name can be anything.
* Completely ignore bundled config with `spring.config.location`. 

```shell
% java -jar target/external-config-1.0.0.jar param1 param2 --spring.config.location=src/config/application-mac.yml 
2022-01-11 21:19:49.682  INFO 12577 --- [           main] c.t.e.ExternalConfigApplication          : Starting ExternalConfigApplication v1.0.0 using Java 17.0.1 on Roberts-Air.fritz.box with PID 12577 (/Users/rlo/OneDrive/Code/the-codeslinger.com-blog-samples/External-Config/target/external-config-1.0.0.jar started by rlo in /Users/rlo/OneDrive/Code/the-codeslinger.com-blog-samples/External-Config)
2022-01-11 21:19:49.683  INFO 12577 --- [           main] c.t.e.ExternalConfigApplication          : No active profile set, falling back to default profiles: default
2022-01-11 21:19:49.870  INFO 12577 --- [           main] c.t.e.ExternalConfigApplication          : Started ExternalConfigApplication in 0.363 seconds (JVM running for 0.532)
-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: param2
Argument: --spring.config.location=src/config/application-mac.yml
-> ExternalConfigProperties
Input path: /Users/the/code
Input path: /Users/slinger
```

**Example 5:** Use "config" dir in current working directory

* Current working directory contains a folder named `config`; Spring Boot automatically searches for this folder.
* Set active profile to find `application-mac.yml`.
* Augment bundled config values with file in `config` folder.

```shell
src % java -jar ../target/external-config-1.0.0.jar param1 --spring.profiles.active=mac        
-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: --spring.profiles.active=mac
-> ExternalConfigProperties
Input path: /Users/the/code
Input path: /Users/slinger
```