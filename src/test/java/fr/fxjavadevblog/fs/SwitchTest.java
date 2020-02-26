package fr.fxjavadevblog.fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
			.build()
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

	@Test
	public void StreamMapTest()
	{
		// switcher could have been written in the map method of the Stream.of(), 
		// but it's defined here for readability only.
		SwitchExpression<Integer, String> switcher = Switch.<Integer, String> start()
				                                           .defaultCase(v -> "ODD")
				                                           .predicate(v -> v % 2 == 0, v -> "EVEN")
				                                           .build();

		// just to check thats everything is fine
		assertNotNull(switcher, "cannot build the switcher");

		// let's run the Stream.map which will call the switcher.
		// the switcher implements Function <R, T> and its apply(T t) method.
		List<String> result = Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
				                    .map(switcher)
				                    .collect(Collectors.toList());

		// few tests on the list
		assertNotNull(result, "the returned list is null, which is unacceptable!");
		assertEquals(10, result.size(), "the returned list size is wrong, which is totally unacceptable!");
		
		// then lets count the EVEN and the ODD to verify the switcher behavior inside a Stream.map().
		
		Map<String, Long> statistics = result.stream().collect(Collectors.groupingBy(String::toString, Collectors.counting()));
		
		assertNotNull(statistics, "the returned map is null, which is unbelievable!");
		assertEquals(5L, statistics.get("ODD").longValue());
		assertEquals(5L, statistics.get("EVEN").longValue());
		
		// it's working!
		
	}
}
