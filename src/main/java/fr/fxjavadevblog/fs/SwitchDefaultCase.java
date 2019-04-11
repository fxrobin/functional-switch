package fr.fxjavadevblog.fs;

import java.util.function.Function;

/**
 * technical interface to restrict method chaining to legal operation order.
 * 
 * @author F.X. Robin
 *
 * @param <T>
 * @param <R>
 */
public interface SwitchDefaultCase <T,R>
{
  /**
   * set the default function that will be executed if no single value nor predicates matches
   * the current value of the switch instance.
   * 
   * @param function
   *    called function when o single value nor predicates matches the current value.
   * @return
   *    current instance of the switch which allows method chaining. 
   */
  SwitchStep<T, R> defaultCase(Function<T, R> function);
}