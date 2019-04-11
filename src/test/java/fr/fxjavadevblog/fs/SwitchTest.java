package fr.fxjavadevblog.fs;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

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
  public void simpleTest()
  {
    String result = Switch.of(5, String.class)
                          .defaultCase(value -> value + " : no case!")
                          .predicate(value -> value > 10 && value < 15, value -> "superior to 10!")
                          .predicate(value -> value >= 0 && value <= 10, value -> value + " is between 0 and 10")
                          .single(10, value -> "10 is the best value!")
                          .single(3, value -> "3 is an exception!")
                          .resolve();

    assertEquals("5 is between 0 and 10", result);
  }

  @Test
  public void staticTest3()
  {
    String result = localSwitch.resolve(3);
    assertEquals("3 is an exception!", result);
  }
  
  @Test
  public void staticTest5()
  {
    String result = localSwitch.resolve(5);
    assertEquals("5 is between 0 and 10", result);
  }
  
  @Test
  public void staticTest9()
  {
    String result = localSwitch.resolve(9);
    assertEquals("9 is between 0 and 10", result);
  }
  
  @Test
  public void staticTest10()
  {
    String result = localSwitch.resolve(10);
    assertEquals("10 is the best value!", result);
  }
  
  @Test
  public void staticTest13()
  {
    String result = localSwitch.resolve(13);
    assertEquals("superior to 10!", result);
  }
  
  @Test
  public void staticTest20()
  {
    String result = localSwitch.resolve(20);
    assertEquals("20 : no case!", result);
  }
}
