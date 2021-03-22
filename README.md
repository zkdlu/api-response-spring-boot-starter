# Spring Api Response
The rest api classifies the HttpMethod and structured the url mapped to the resource. and have standard structure to response data.
Spring Api Response provides a standard structure to response data and exception handling 

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
     
```

....
