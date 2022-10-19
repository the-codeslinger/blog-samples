Spring Boot Validation Error Message
====================================

This sample project demonstrates how to create field-specific error messages
for a custom `ConstraintValidator` that targets a class instead of a single
field.

See the accompanying [blog post][blog] for explanations to the code.

## Usage

Custom error messages for a specific field error.
```shell
% curl -i -H "content-type: application/json" \
    -d '{"artist":"Insomnium","album":"Argent Moon","year":2021,"genre":"Volksmusik","tracks":[{"track":3,"title":"The antagonist"}]}' \
    -X POST http://localhost:8080/albums
HTTP/1.1 400
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 16 Oct 2022 18:30:01 GMT
Connection: close

{
  "code":400,
  "messages":[
    "genre: This band does not play this type of music.",
    "genre: 'Melodic Death Metal' is expected for artist Insomnium."
  ]
}
```

Default error message defined by the `@interface`.

```shell
% curl -i -H "content-type: application/json" \
    -d '{"artist":"Insomnium","album":"Argent Moon","year":2021,"genre":"Melodic Death Metal","tracks":[]}' \
    -X POST http://localhost:8080/albums
HTTP/1.1 400
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 16 Oct 2022 18:19:05 GMT
Connection: close

{
  "code":400,
  "messages":[
    "The album does not match our level of quality."
  ]
}
```

[blog]: http://the-codeslinger.com/2022/10/19/spring-boot-custom-field-error-messages-in-class-based-custom-bean-constraintvalidator/