package org.dnowogorski;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
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

    private String createUrl(String base, String user, String repo) {
        return String.format("%s/%s/%s", base, user, repo);
    }
}
