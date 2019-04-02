package org.dnowogorski;

import io.vavr.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionsTest {

    @Test
    public void shouldDropSomeElements() {
        assertThat(List.of(1, 2, 3)
                .appendAll(List.of(16, 18, 20))
                .dropUntil(num -> num % 4 == 0)).containsExactly(16, 18, 20);
    }

    @Test
    public void shouldScanLeft() {
        assertThat(List.of("John", "Steven", "Ann")
                .scanLeft("Ann", (s1, s2) -> s1.toLowerCase() + s2.toUpperCase()))
                .containsExactly("Ann", "annJOHN", "annjohnSTEVEN", "annjohnstevenANN");
    }
}
