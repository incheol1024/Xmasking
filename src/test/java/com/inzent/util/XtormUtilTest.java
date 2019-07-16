package com.inzent.util;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class XtormUtilTest {

    private String uploadLocalFile;

    private String downloadLocalFile;

    @Before
    public void setUp() {
        uploadLocalFile = "D:\\app\\xtorm-test\\upload\\test.txt";
        downloadLocalFile = "D:\\app\\xtorm-test\\download";
    }


    @Test
    public void uploadElementTestForSuccess() {
        Optional<String> optional = XtormUtil.uploadElement("D:\\app\\xtorm-test\\upload\\test.txt");

        Predicate<Optional<String>> optionalPredicate = (Optional<String> optionalS) -> optionalS.isPresent();
        Assertions.assertThat(optionalPredicate).accepts(optional);

        Predicate<String> stringPredicate = (String elementId) -> elementId.matches("([0-9]{14})(..)") && elementId.length() == 16;
        Assertions.assertThat(stringPredicate).accepts(optional.orElse(""));

    }

    @Test
    public void uploadElementTestForFail() {
        Optional<String> optional = XtormUtil.uploadElement("D:\\app\\xtorm-test\\upload\\test.tt");

        Predicate<Optional<String>> optionalPredicate = (Optional<String> stringOptional) -> !stringOptional.isPresent();
        Assertions.assertThat(optionalPredicate).accepts(optional);
    }


    @Test
    public void downloadElementTest() {

        int actual = XtormUtil.downloadElement("2019071604364600", "D:\\app\\xtorm-test\\download\\2019071604364600");
        IntPredicate intPredicate = expected -> actual == expected;
        Assertions.assertThat(intPredicate).accepts(0);

    }
}
