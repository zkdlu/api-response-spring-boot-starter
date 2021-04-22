# Spring Api Response
The rest api classifies the HttpMethod and structured the url mapped to the resource. and have standard structure to response data.
Spring Api Response provides a standard structure to response data and exception handling 

## Maven configuration
Add the Maven dependency:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
    <groupId>com.github.zkdlu</groupId>
    <artifactId>api-response-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Gradle configuration
Add the Gradle dependency:
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}
dependencies {
	implementation 'com.github:zkdlu:api-response-spring-boot-starter:Tag'
}
```

## Getting Started
Here is a quick teaser in Spring boot Application:

```java
// Add Enable Annotation
@EnableResponse
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

// Defination Custom Exception
public class CustomException extends RuntimeException {
}

// application.yml
spring:
  response:
    success:
      code: HELLO
      msg: SUCCESS
    exceptions:
      custom1:
        code: ERR1
        msg: 'Something went wrong'
        type: com.example.demo.CustomException
      ..
      
// Some Controller
public class DemoController {
  @GetMapping("/")
  public String test() {
    return "demo"
  }
  
  @GetMapping("/model")
  public MyModel test2() {
    return MyModel.prototype();
  }
  
  @GetMapping("/exception")
  public String test3() {
    throw new CustomException();
  }
  
  @GetMapping("/list")
  public List<MyModel> test4() {
    return Array.asList(MyModel.prototype()); 
  }
}
```

The response structure

- Before
```json
{
  "msg": "hello"
}
```

- After
```json
# IF Sucess
{
  "success": true,
  "code": "<success code> | SUCCESS",
  "msg": "<success msg> | SUCCESS",
  "data": {
    "msg": "hello"
  }
}

# IF Thrown Exception
{
  "success": false,
  "code": "<fail code by your exception>",
  "msg": "<fail msg by your exception>",
  "data": 
}

```

## Build from Source
You don’t need to build from source (binaries in jitpack.io), but if you want to try out the latest, Use the built-in gradle wrapper. You don't need gradle, jdk, anything.

```bash
# macos
$ chomod +x ./gradlew
$ ./gradlew install
```

