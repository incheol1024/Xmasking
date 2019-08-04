package com.inzent.agent;

import com.inzent.util.AppProperty;

import java.util.Properties;

public interface Agent extends Runnable {

    Properties properties = AppProperty.getProperties();

}
