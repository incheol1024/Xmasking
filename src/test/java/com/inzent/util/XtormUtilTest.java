package com.inzent.util;

import com.inzent.Boot;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Optional;
import java.util.Random;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class XtormUtilTest {

    private String testElementId;

    private String uploadLocalFile;

    private String downloadLocalFile;


    @Before
    public void setUp() {
        testElementId = "2019071607125100";
        uploadLocalFile = "D:\\app\\xtorm-test\\upload\\test.xlsx";
        downloadLocalFile = "D:\\app\\xtorm-test\\download\\" + testElementId;
    }


    @Test
    public void uploadElementTestForSuccess() {
        Optional<String> optional = XtormUtil.uploadElement(uploadLocalFile);

        Predicate<Optional<String>> optionalPredicate = (Optional<String> optionalS) -> optionalS.isPresent();
        Assertions.assertThat(optionalPredicate).accepts(optional);

        Predicate<String> stringPredicate = (String elementId) -> elementId.matches("([0-9]{14})(..)") && elementId.length() == 16;
        Assertions.assertThat(stringPredicate).accepts(optional.orElse(""));

    }

    @Test
    public void uploadElementMany() {

        Temporal startTemporal = Instant.now();
        Stream.generate(() -> uploadLocalFile).limit(1000).parallel()
                .map((localFile) -> XtormUtil.uploadElement(localFile))
                .map(optionalS -> optionalS.orElseThrow(RuntimeException::new))
                .peek((result) -> System.out.println(result))
                .forEach((elementId) -> System.out.println(elementId));
        ;
        Temporal endTemporal = Instant.now();
// no parallel 88263ms AUTOSTART 12519ms
// parallel 74352ms AUTOSTART 11487ms

        System.out.println(Duration.between(startTemporal, endTemporal).toMillis());
    }

    @Test
    public void uploadElementTestForFail() {
        Optional<String> optional = XtormUtil.uploadElement(uploadLocalFile + "xx");

        Predicate<Optional<String>> optionalPredicate = (Optional<String> stringOptional) -> !stringOptional.isPresent();
        Assertions.assertThat(optionalPredicate).accepts(optional);
    }

/*
    @Test
    public void downloadElementTestForSuccess() {

        int actual = XtormUtil.downloadElement("2019071607125100", downloadLocalFile);
        IntPredicate intPredicate = expected -> actual == expected;
        Assertions.assertThat(intPredicate).accepts(0);

    }

    @Test
    public void downloadElementTestForFail() {

        int actual = XtormUtil.downloadElement("dfdfdf", downloadLocalFile);
        IntPredicate intPredicate = expected -> actual == expected;
        Assertions.assertThat(intPredicate).accepts(3);

    }*/


    @Test
    public void downloadTest() {

        Boot boot = new Boot();
        boot.boot();
        for( int i =0 ; i < 100; i++) {
            XtormUtil.downloadElement(XtormUtil.XtormServer.MASK_XTORM, "2019090417072200", "D:\\Temp\\" + i + ".txt");
        }

    }

}
