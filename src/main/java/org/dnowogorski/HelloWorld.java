package org.dnowogorski;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class HelloWorld {
    public static void main(String[] args) {
        Tuple2<String, Integer> tuple2 = Tuple.of("Java", 8);

        System.out.println(tuple2._1());
    }
}
