package org.dnowogorski;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TupleTest {

    private static final String JAVA = "Java";
    private static final int JAVA_VERSION = 8;
    private static final String SPRING = "spring";
    private static final int SPRING_VERSION = 4;
    private Tuple2<String, Integer> tuple2;
    private Tuple4<String, Integer, String, Integer> tuple4;
    
    @Before
    public void createTuple() {
        tuple2 = Tuple.of(JAVA, JAVA_VERSION);
        tuple4 = Tuple.of(JAVA, JAVA_VERSION, SPRING, SPRING_VERSION);
    }
    
    @Test
    public void shouldCreateTuple() {
        assertThat(tuple2._1, is(JAVA));
        assertThat(tuple2._2, is(JAVA_VERSION));
    }

    @Test
    public void shouldMapTuple2() {
        Tuple2<String, Integer> mapped = tuple2.map(String::toUpperCase, i -> i + 3);

        assertThat(mapped._1, is("JAVA"));
        assertThat(mapped._2, is(11));
    }

    @Test
    public void shouldMapTuple4() {
        Tuple4<String, Integer, Character, String> mapped = tuple4.map(String::toLowerCase, i -> i + 2, s -> s.charAt(0), Integer::toBinaryString);

        assertThat(mapped._1, is("java"));
        assertThat(mapped._2, is(10));
        assertThat(mapped._3, is('s'));
        assertThat(mapped._4, is("100"));
    }

    @Test
    public void shouldMapTupleUsingOneMapper() {
        Tuple2<String, Integer> mapped = tuple2.map((s, i) -> Tuple.of(s.toLowerCase(), i - 3));

        assertThat(mapped._1, is("java"));
        assertThat(mapped._2, is(5));
    }

    @Test
    public void shouldTransformTuple() {
        String transformed = tuple2.apply((s, i) -> s.toUpperCase() + " " + i);

        assertThat(transformed, is("JAVA 8"));
    }
}
