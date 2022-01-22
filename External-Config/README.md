Externalize Spring Boot Command Line Project Configuration
==========================================================

All examples use `spring.config.additional-location` with which you only override the values you wish to change.
You can also use `spring.config.location` which expects you to provide a complete configuration file, not just overrides.

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
Input path: /home/bundled/thecode
Output path: /home/bundled/slinger
```

**Example 2:** Use external profile-specific config in folder without setting profile

* Uses JVM parameters `-D`.
* Specifies a folder; config filename must be `application.yml` or `application-<profile>.yml`.
* No active profile is set; searches for `application.yml` and ignores profile-specific config.
* Because no external config is found (only `application-mac.yml` exists), bundled config is used.

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
Input path: /home/bundled/thecode
Output path: /home/bundled/slinger
```

**Example 3:** Use external profile-specific config in folder with setting profile

* Uses JVM parameters `-D`.
* Specifies a folder; config filename must be `application.yml` or `application-<profile>.yml`.
* Set active profile to find `application-mac.yml`.
* Augment bundled config values with `spring.config.additional-location`.

```shell
% java -Dspring.config.additional-location=src/config/ -Dspring.profiles.active=mac -jar target/external-config-1.0.0.jar param1 param2

-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: param2
-> ExternalConfigProperties
Input path: /Users/mac/thecode
Output path: /Users/mac/slinger
```

**Example 4:** Use external profile-specific config in folder with setting profile

* Uses application parameters instead of JVM parameters.
* Specifies a folder; config filename must be `application.yml` or `application-<profile>.yml`.
* Set active profile to find `application-mac.yml`.
* Completely ignore bundled config with `spring.config.location`.

```shell
% java -jar target/external-config-1.0.0.jar param1 param2 --spring.profiles.active=mac --spring.config.location=src/config/

-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: param2
Argument: --spring.profiles.active=mac
Argument: --spring.config.location=src/config/
-> ExternalConfigProperties
Input path: /Users/mac/thecode
Output path: /Users/mac/slinger
```

**Example 5:** Use explicit filename, regardless of name or active profile

* Uses application parameters instead of JVM parameters.
* Specifies a specific file; name can be anything.
* Completely ignore bundled config with `spring.config.location`. 

```shell
% java -jar target/external-config-1.0.0.jar param1 param2 --spring.config.location=src/config/application-mac.yml 

-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: param2
Argument: --spring.config.location=src/config/application-mac.yml
-> ExternalConfigProperties
Input path: /Users/mac/thecode
Output path: /Users/mac/slinger
```

**Example 5:** Use "config" dir in current working directory

* Current working directory contains a folder named `config`; Spring Boot automatically searches for this folder.
* Set active profile to find `application-mac.yml`.
* Augment bundled config values with file in `config` folder.

```shell
src % ls
config	main

src % ls config 
application.yml

src % java -jar ../target/external-config-1.0.0.jar param1 --spring.profiles.active=mac
-> AppRunner.run() Command Line Arguments
Argument: param1
Argument: --spring.profiles.active=mac
-> ExternalConfigProperties
Input path: /Users/mac/thecode
Output path: /Users/mac/slinger
```