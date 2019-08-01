package com.inzent.stream;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class StreamTest {

    Stream<String> stringStreasm;

    @Before
    public void setUp() throws IOException {

        stringStreasm = Files.lines(Paths.get("D:\\app\\naver.html"), Charset.forName("UTF-8"));


    }

    @Test
    public void parallelStreamTest() {

        long start = System.currentTimeMillis();
        int tagStringNumber = stringStreasm.parallel()
                .mapToInt((tagString) -> tagString.split("<").length - 1)
                .sum();
        long end = System.currentTimeMillis();

        System.out.println(end - start);


    }


    @Test
    public void streamTest() {

        long start = System.currentTimeMillis();
        int tagStringNumber = stringStreasm
                .mapToInt((tagString) -> tagString.split("<").length - 1)
                .sum();
        long end = System.currentTimeMillis();
        System.out.println(end - start);


    }

    @Test
    public void arraysStream() {

        int[] intArr = new int[1000];
        Arrays.parallelSetAll(intArr, (i) -> (i + 1) * 10000 / 21);

        for (int a : intArr) {
            System.out.println(a);
        }
    }

}
