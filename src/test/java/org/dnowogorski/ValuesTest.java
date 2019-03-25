package org.dnowogorski;

import io.vavr.API;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
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

    @Test
    public void shouldTry() {
        String somethingElse = "Some other value";
        assertThat(Try.of(this::bunchOfWork).getOrElse(somethingElse)).isEqualTo(somethingElse);
    }

    @Test
    public void shouldTryAndRecover() {
        String result = Try.of(this::bunchOfWork).recover(x -> API.Match(x).of(
                Case($(instanceOf(RuntimeException.class)), this::handleException)
        )).get();

        assertThat(result).isEqualTo("Something bad happened...");
    }

    private String bunchOfWork() {
        throw new RuntimeException("Something bad happened...");
    }

    private String handleException(RuntimeException ex) {
        return ex.getMessage();
    }
}
