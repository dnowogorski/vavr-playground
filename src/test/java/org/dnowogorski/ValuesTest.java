package org.dnowogorski;

import io.vavr.API;
import io.vavr.Lazy;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

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

    @Test
    public void shouldTryAndThenTry() {
        String defaultResult = "It's too heavy";

        String result = Try.of(this::bunchOfWork)
                .andThenTry(this::heavyTask)
                .getOrElse(defaultResult);

        assertThat(result).isEqualTo(defaultResult);
    }

    @Test
    public void shouldLazyLoadValue() {
        Lazy<Integer> lazyValue = Lazy.of(() -> ThreadLocalRandom.current().nextInt());

        assertThat(lazyValue.isEvaluated()).isEqualTo(false);
        Integer randomValue = lazyValue.get();
        assertThat(lazyValue.isEvaluated()).isEqualTo(true);
        assertThat(lazyValue.get()).isEqualTo(randomValue);
    }

    @Test
    public void shouldGetRightProjectionAndDoSthWithIt() {
        int result = compute(32).right()
                .map(i -> i * 2)
                .getOrElse(0);

        assertThat(result).isEqualTo(128);
    }

    @Test
    public void shouldGetLeftProjectionAndDoSthWithIt() {
        String result = compute(-1).left()
                .map(String::toUpperCase)
                .get();

        assertThat(result).isEqualTo("VALUE CANNOT BE NEGATIVE");
    }

    private Either<String, Integer> compute(int value) {
        return value <= 0 ? Either.left("Value cannot be negative") : Either.right(value * 2);
    }

    private void heavyTask() {
        throw new RuntimeException("Bad but recoverable");
    }

    private String bunchOfWork() {
        throw new RuntimeException("Something bad happened...");
    }

    private String handleException(RuntimeException ex) {
        return ex.getMessage();
    }
}
