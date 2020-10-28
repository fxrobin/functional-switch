[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/fxrobin/functional-switch)

# functional-switch

## Introduction

A Switch/Case converted into functional programming with Java. (JDK 8 and after).

The original blog article is available only in French here : https://www.fxjavadevblog.fr/functional-switch/

## Usages

### Basic usage

```java
String result = Switch.of(initialValue, String.class)
                      .defaultCase(value -> value + " : no case!")
                      .single(10, value -> "10 is the best value!")
                      .single(3, value -> "3 is an exception!")
                      .resolve();
```

### Advanced usage

```java
String result = Switch.of(initialValue, String.class)
                      .defaultCase(value -> value + " : no case!")
                      .predicate(value -> value > 10 && value < 15, value -> "superior to 10!")
                      .predicate(value -> value >= 0 && value <= 10, value -> value + " is between 0 and 10")
                      .single(10, value -> "10 is the best value!")
                      .single(3, value -> "3 is an exception!")
                      .resolve();
```

### Unit Tests

```java
public class SwitchTest
{
  public static SwitchExpression<Integer, String> localSwitch;

  @BeforeAll
  public static void init()
  {
    localSwitch = Switch.<Integer, String> start()
                        .defaultCase(value -> value + " : no case!")
                        .predicate(value -> value > 10 && value < 15, value -> "superior to 10!")
                        .predicate(value -> value >= 0 && value <= 10, value -> value + " is between 0 and 10")
                        .single(10, value -> "10 is the best value!")
                        .single(3, value -> "3 is an exception!")
                        .build();
  }

  @Test
  public void staticTest3()
  {
    assertEquals("3 is an exception!", localSwitch.resolve(3));
  }
  
  @Test
  public void staticTest5()
  {
    assertEquals("5 is between 0 and 10", localSwitch.resolve(5));
  }
}
```






