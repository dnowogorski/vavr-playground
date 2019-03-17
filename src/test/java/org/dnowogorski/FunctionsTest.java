package org.dnowogorski;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Function5;
import io.vavr.control.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FunctionsTest {

    private static final Function1<Integer, Integer> INCREMENT = a -> a + 1;
    private static final Function1<Integer, Integer> MULTIPLY_BY_TWO = a -> a * 2;

    @Test
    public void shouldCreateFunction2() {
        Function2<Integer, Integer, String> sum = (a, b) -> Integer.toBinaryString(a + b);

        assertThat(sum.apply(2, 7), is("1001"));
    }

    @Test
    public void shouldCreateFunction3() {
        Function3<String, String, String, String> url = Function3.of(this::createUrl);

        assertThat(url.apply("https://github.com", "dnowogorski", "vavr-playground"), is("https://github.com/dnowogorski/vavr-playground"));
    }

    @Test
    public void shouldComposeFunctions() {
        // First increment then multiply by two
        Function1<Integer, Integer> composed = INCREMENT.andThen(MULTIPLY_BY_TWO);

        assertThat(composed.apply(2), is(6));
    }

    @Test
    public void shouldComposeFunctionsV2() {
        Function1<Integer, Integer> decrement = a -> a - 1;
        // First increment then multiply by two then decrement
        Function1<Integer, Integer> composed = decrement.compose(MULTIPLY_BY_TWO.compose(INCREMENT));

        assertThat(composed.apply(3), is(7));
    }

    @Test
    public void shouldLiftPartialFunction() {
        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

        assertThat(safeDivide.apply(1, 0).isEmpty(), is(true));
        assertThat(safeDivide.apply(4, 2).getOrElse(0), is(2));
    }

    @Test
    public void shouldLiftFunctionThrowingException() {
        Function2<Integer, Integer, Option<Integer>> saveSum = Function2.lift(this::sum);

        assertThat(saveSum.apply(-1, 1).isEmpty(), is(true));
    }

    @Test
    public void shouldPartiallyApplyFunction() {
        Function5<Integer, Integer, Integer, Integer, Integer, Integer> fiveAddendsSum = (a, b, c, d, e) -> a + b + c + d + e;

        Function2<Integer, Integer, Integer> six = fiveAddendsSum.apply(2, 3, 1); // a,b,c fixed to 2, 3, 1

        assertThat(six.apply(4, 3), is(13));
    }

    @Test
    public void shouldPartiallyApplyFunctionUsingCurrying() {
        Function2<Integer, Integer, Integer> sum = this::sum;

        // Works this way only for Function2
        Function1<Integer, Integer> addTwo = sum.curried().apply(2);

        assertThat(addTwo.apply(1), is(3));
    }

    @Test
    public void shouldCurryFunction() {
        Function3<Integer, Integer, Integer, Integer> sum = (a, b, c) -> a + b + c;

        Function1<Integer, Function1<Integer, Integer>> addTwo = sum.curried().apply(2);

        assertThat(addTwo.apply(3).apply(4), is(9));
    }

    private int sum(int a, int b) {
        if (a < 0 || b < 0) throw new IllegalArgumentException("Only positive values allowed");
        return a + b;
    }

    private String createUrl(String base, String user, String repo) {
        return String.format("%s/%s/%s", base, user, repo);
    }
}
