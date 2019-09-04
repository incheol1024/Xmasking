package com.inzent;

import org.junit.Test;

import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class StreamTset {


    @Test
    public void StreamToListTest() throws InterruptedException {

        boolean a =
        Stream.of(new Random().nextInt(100)).limit(100)
                .peek((integer) -> System.out.println(integer) )
//                .max(Compsarator.comparingInt(Integer::intValue))
                .noneMatch((integer) -> integer == 101);


        System.out.println(a);
    }
}
