package com.inzent;

import com.sun.javafx.image.IntPixelGetter;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ThreadTest {

    boolean done;

    @Before
    public void setUp() {
        done = false;
    }

    @Test
    public void ThreadTest() {

        int pnumber = Runtime.getRuntime().availableProcessors();

        System.out.println(pnumber);


    }

    @Test
    public void ThreadRun() {

        Runnable hello = () -> {
            for (int i = 0; i < 1000; i++) {
//                System.out.println("hello:" + i);
                if (i == 999)
                    System.out.println("hello" + i);
            }
        };

        Runnable goodbye = () -> {
            for (int i = 0; i < 1000; i++) {
//                System.out.println("goodbye:" + i);
                if (i == 999)
                    System.out.println("goodbye" + i);

            }
        };


        Executor executor = Executors.newCachedThreadPool();
        executor.execute(hello);
        executor.execute(goodbye);
    }

    @Test
    public void fileNameTest() throws ExecutionException, InterruptedException {

        Callable<Integer> file1 = () -> {
            System.out.println("callable is start?");
            Stream<String> lines = Files.lines(Paths.get("C:\\Users\\User\\Downloads\\Templates.txt"), Charset.forName("MS949"));
            return (int) (lines.filter((string) -> string.matches("div")).count());

        };


        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<Integer> integerFuture = executor.submit(file1);
//        System.out.println(integerFuture.get());
        System.out.println(integerFuture.isDone());


    }

    @Test
    public void 가시성테스트() {

        Runnable hello = () -> {
            for (int i = 0; i < 10000; i++) {
                System.out.println("hello " + i);
            }
            done = true;
        };

        Runnable goodbye = () -> {

            int i = 0;
            while(!done)
                i++;
            System.out.println("goodbye" + i);
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(hello);
        executorService.execute(goodbye);
    }
}
