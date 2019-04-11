package fr.fxjavadevblog.fs;

import java.util.function.Function;
import java.util.function.Predicate;


/**
 * technical interface to restrict method chaining to use the appropriate operation order.
 * 
 * @author F.X. Robin
 *
 * @param <T>
 * @param <R>
 */
public interface SwitchStep <T,R>
{
  /**
   * binds a value with a function to execute.
   * 
   * @param value
   *    value to test.
   * @param function
   *    function to run if the test succeeds.
   * @return
   *    current instance of the switch which allows method chaining.
   */
  SwitchStep<T, R> single(T value, Function<T, R> function);
  
  /**
   * appends a predicate mapped with a function.
   * 
   * @param predicate
   *    predicate that will be evaluated with the value of the current switch.
   * @param function
   *    function that will be executed if the predicate returns true.
   * @return
   *    current instance of the switch which allows method chaining. 
   */
  SwitchStep<T, R> predicate(Predicate<T> predicate, Function<T, R> function);
  
  /**
   * last operation of the switch method chaining which executes the flow
   * of the rules looking first for single value, then predicates, then the
   * default function.
   * 
   * @return
   */
  R resolve();
}