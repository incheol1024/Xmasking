package com.inzent.agent;

import com.inzent.Boot;
import com.inzent.agent.download.DownloadAgent;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DownloadAgentTest implements DownloadAgent {

    @Override
    public void run() {

    }

    @Before
    public void setUp() throws Exception {

        Boot boot = new Boot();
        boot.boot();
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void getDownRootPathTest() {
        String rootPath = this.getDownRootPath();
        Predicate<String> stringPredicate = actual -> actual.equals(properties.getProperty("DOWN_ROOT_PATH"));
        Assertions.assertThat(stringPredicate).accepts(rootPath);
    }

    @Test
    public void getDatePathTest() {
        String datePath = this.getDatePath();
        Predicate<String> stringPredicate = actual -> actual.equals(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        Assertions.assertThat(stringPredicate).accepts(datePath);
    }

    @Test
    public void getDownPathTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Stream<DownloadAgentTest> stream = Stream.generate(() -> this).limit(1000);
        executorService.execute(() -> {
            stream
                    .map(DownloadAgent::getDownPath)
                    .forEach((path) -> System.out.println(Thread.currentThread().getName() + "::" + path));
        });

        executorService.execute(() -> {
            stream
                    .map(DownloadAgent::getDownPath)
                    .forEach((path) -> System.out.println(Thread.currentThread().getName() + "::" + path));
        });

        executorService.execute(() -> {
            stream
                    .map(DownloadAgent::getDownPath)
                    .forEach((path) -> System.out.println(Thread.currentThread().getName() + "::" + path));
        });

        executorService.execute(() -> {
            stream
                    .map(DownloadAgent::getDownPath)
                    .forEach((path) -> System.out.println(Thread.currentThread().getName() + "::" + path));
        });

    }


}