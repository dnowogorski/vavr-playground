package org.dnowogorski;

import io.vavr.test.Arbitrary;
import io.vavr.test.Property;
import org.junit.Test;

/**
 * https://en.wikipedia.org/wiki/Property_testing
 */
public class PropertyTesting {

    @Test
    public void shouldReturnTrueForSquares() {
        Arbitrary<Integer> ints = Arbitrary.integer();

        Property.def("square(int) >= 0")
                .forAll(ints)
                .suchThat(i -> i * i >= 0)
                .check()
                .isSatisfied();
    }
}
