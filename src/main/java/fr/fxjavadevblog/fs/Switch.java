package fr.fxjavadevblog.fs;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * an implementation of a "switch-like" structure which can return a value and
 * allows functional calls. The switch flow is build through method chaining.
 * 
 * @author F.X. Robin
 *
 * @param <T>
 *          type of the tested value
 * @param <R>
 *          type of the returned value
 */
public class Switch<T, R> implements SwitchDefaultCase<T, R>, SwitchStep<T, R>, SwitchExpression<T, R>
{

  /**
   * function executed when no value has been found.
   */
  private Function<T, R> defaultCase;

  /**
   * value to evaluate.
   */
  private T value;

  /**
   * map of functions keyed by the matching value.
   */
  private Map<T, Function<T, R>> singleValuefunctions = new HashMap<>();

  /**
   * map of functions keyed by predicates. All the predicates are tested.
   */
  private List<Entry<Predicate<T>, Function<T, R>>> predicates = new LinkedList<>();

  /**
   * hidden constructor. the "of" method is the only starting point for building
   * an instance.
   * 
   */
  private Switch()
  {

  }

  /**
   * initiates the switch flow with the value to test and the returning type.
   * 
   * @param value
   *          value to test
   * @param clazz
   *          returning type
   * @return a new instance of the switch which allows method chaining
   */
  public static <T, R> SwitchDefaultCase<T, R> of(T value, Class<R> clazz)
  {
    Switch<T, R> switchExpression = new Switch<>();
    switchExpression.value = value;
    return switchExpression;
  }

  /**
   * sets the returning type for late calling of the resolve(T t) method.
   * 
   * @param clazz
   *          returning type
   * @return a new instance of the switch which allows method chaining
   */
  public static <T, R> SwitchDefaultCase<T, R> start()
  {
    return new Switch<T, R>();
  }

  /**
   * @see {@link SwitchDefaultCase#defaultCase(Function)}
   */
  @Override
  public SwitchStep<T, R> defaultCase(Function<T, R> function)
  {
    this.defaultCase = function;
    return this;
  }

  /**
   * @see {@link SwitchStep#resolve()}
   */
  @Override
  public R resolve()
  {
    return singleValuefunctions.containsKey(value) ? singleValuefunctions.get(value).apply(value) : findAndApplyFirstPredicate();
  }
  
  /**
   * @see {@link SwitchExpression#resolve(T)}
   */
  @Override
  public R resolve(T value)
  {
    this.value = value;
    return resolve();
  }
  
  public SwitchExpression<T, R> build()
  {
    return this;
  }

  private R findAndApplyFirstPredicate()
  {
    for (Entry<Predicate<T>, Function<T, R>> entry : predicates)
    {
      if (entry.getKey().test(value))
      { 
        return entry.getValue().apply(value); 
      }
    }
    return this.defaultCase.apply(value);
  }

  /**
   * @see {@link SwitchStep#single(Object, Function)}
   */
  @Override
  public SwitchStep<T, R> single(T value, Function<T, R> function)
  {
    singleValuefunctions.put(value, function);
    return this;
  }

  /**
   * @see {@link SwitchStep#predicate(Predicate, Function)}
   */
  @Override
  public SwitchStep<T, R> predicate(Predicate<T> predicate, Function<T, R> function)
  {
    SimpleEntry<Predicate<T>, Function<T, R>> simpleEntry = new SimpleEntry<>(predicate, function);
    predicates.add(simpleEntry);
    return this;
  }
}
