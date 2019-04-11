package fr.fxjavadevblog.fs;

/**
 * represents a fully constructed Switch instance which can resolve a specific value.
 * 
 * @author F.X. Robin
 *
 * @param <T>
 * @param <R>
 */
public interface SwitchExpression <T, R>
{
  /**
   * last operation of the switch method chaining which executes the flow of the
   * rules looking first for single value, then predicates, then the default
   * function.
   * 
   * @param value
   *          value to test
   * @return
   *          result of the Switch flow.
   */
  R resolve(T value);
}
