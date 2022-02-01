Externalize Spring Boot Command Line Project Configuration With Apache Commons CLI
==================================================================================

**Example 1:** No special "help" handling

If not explicitly programmed into the application, `--help` will not work.

````shell
% java -jar target/external-config-commons-cli-1.0.0.jar help

-> AppRunner.run() Command Line Arguments
Argument: help
-> ExternalConfigProperties
Input path: /home/bundled/thecode
Output path: /home/bundled/slinger
-> Parsing Arguments With Apache Commons CLI
Missing required options: i, o
````

**Example 2:** Implement help system

Important: `stopAtNonOption=true` for `parser.parse(..., ..., true);`

Help works now.
```shell
% java -jar target/external-config-commons-cli-1.0.0.jar -h          

-> AppRunner.run() Command Line Arguments
Argument: -h
-> ExternalConfigProperties
Input path: /home/bundled/thecode
Output path: /home/bundled/slinger
-> Parsing Help With Apache Commons CLI
usage: external-config-commons-cli
 -f,--file <arg>     (Optional) Filename in source folder.
 -h,--help           Print this message.
 -i,--input <arg>    Source folder to read from.
 -o,--output <arg>   Destination folder to write to.
```

No arguments given results in error.
```shell
 % java -jar target/external-config-commons-cli-1.0.0.jar   

-> AppRunner.run() Command Line Arguments
-> ExternalConfigProperties
Input path: /home/bundled/thecode
Output path: /home/bundled/slinger
-> Parsing Help With Apache Commons CLI
-> Parsing Arguments With Apache Commons CLI
Missing required options: i, o
```

Only optional argument given results in error.
```shell
% java -jar target/external-config-commons-cli-1.0.0.jar -f file-name.json

-> AppRunner.run() Command Line Arguments
Argument: -f
Argument: file-name.json
-> ExternalConfigProperties
Input path: /home/bundled/thecode
Output path: /home/bundled/slinger
-> Parsing Help With Apache Commons CLI
-> Parsing Arguments With Apache Commons CLI
Missing required options: i, o
```

**Example 3:** Specify externalized config as 1st app parameter

```shell
% java -jar target/external-config-commons-cli-1.0.0.jar --spring.config.additional-location=src/config/application-mac.yml -i in -o out
-> AppRunner.run() Command Line Arguments
Argument: --spring.config.additional-location=src/config/application-mac.yml
Argument: -i
Argument: in
Argument: -o
Argument: out
-> ExternalConfigProperties
Input path: /Users/mac/thecode
Output path: /Users/mac/slinger
-> Parsing Help With Apache Commons CLI
-> Parsing Arguments With Apache Commons CLI
Missing required options: i, o
```

**Example 4:** Specify externalized config as last app parameter

```shell
% java -jar target/external-config-commons-cli-1.0.0.jar -i in -o out --spring.config.additional-location=src/config/application-mac.yml
-> AppRunner.run() Command Line Arguments
Argument: -i
Argument: in
Argument: -o
Argument: out
Argument: --spring.config.additional-location=src/config/application-mac.yml
-> ExternalConfigProperties
Input path: /Users/mac/thecode
Output path: /Users/mac/slinger
-> Parsing Help With Apache Commons CLI
-> Parsing Arguments With Apache Commons CLI
Found option i with value in
Found option o with value out
```

**Example 5:** Specify externalized config as JVM parameter

```shell
% java -Dspring.config.additional-location=src/config/application-mac.yml -jar target/external-config-commons-cli-1.0.0.jar -i in -o out
-> AppRunner.run() Command Line Arguments
Argument: -i
Argument: in
Argument: -o
Argument: out
-> ExternalConfigProperties
Input path: /Users/mac/thecode
Output path: /Users/mac/slinger
-> Parsing Help With Apache Commons CLI
-> Parsing Arguments With Apache Commons CLI
Found option i with value in
Found option o with value out
```