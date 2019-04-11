package fr.fxjavadevblog.fs;

import fr.fxjavadevblog.fs.Switch;

public class MainProg
{

  public static void main(String[] args)
  {
    for (int i = 0; i < 20; i++)
    {
      String result = Switch.of(i, String.class)
                            .defaultCase(value -> value + " : no case!")
                            .predicate(value -> value > 10 && value < 15, value -> "superior to 10!")
                            .predicate(value -> value >= 0 && value <= 10, value -> value + " is between 0 and 10")
                            .single(10, value -> "10 is the best value!")
                            .single(3, value -> "3 is an exception!")
                            .resolve();
      System.out.println(result);
    }
  }
}
