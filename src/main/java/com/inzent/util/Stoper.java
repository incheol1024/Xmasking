package com.inzent.util;

public final class Stoper {

    private volatile static boolean stopping = false;

    public static void stop() {
        stopping = true;
    }

    public static boolean isStop() {
        return stopping;
    }
}
