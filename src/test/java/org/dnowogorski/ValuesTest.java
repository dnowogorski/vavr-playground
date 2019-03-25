package org.dnowogorski;

import io.vavr.control.Option;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ValuesTest {

    @Test
    public void shouldThrowNPEWhenMappingNull() {
        Option<String> maybeFoo = Option.of("foo");

        assertThatExceptionOfType(NullPointerException.class).isThrownBy(
                () -> maybeFoo.map(foo -> (String) null)
                        .map(String::toUpperCase));
    }

    @Test
    public void shouldDealWithNPEUsingFlatMap() {
        assertThat(Option.of("foo")
                .map(foo -> (String) null)
                .flatMap(Option::of)
                .map(String::toLowerCase)
                .isEmpty()
        ).isEqualTo(true);
    }
}
