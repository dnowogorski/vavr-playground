package org.dnowogorski;

import io.vavr.Function2;
import io.vavr.Function3;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FunctionsTest {

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

    private String createUrl(String base, String user, String repo) {
        return String.format("%s/%s/%s", base, user, repo);
    }
}
