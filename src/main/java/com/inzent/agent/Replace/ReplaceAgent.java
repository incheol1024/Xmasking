package com.inzent.agent.Replace;

import com.inzent.agent.Agent;
import com.inzent.dto.agent.ReplaceTargetDto;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface ReplaceAgent extends Agent{

    String REPLACE_AGENT_THREAD = properties.getProperty("REPLACE_AGENT_THREAD");

    String REPLACE_ARCHIVE_MODE = properties.getProperty("REPLACE_ARCHIVE_MODE");

    String REPLACE_ARCHIVE = properties.getProperty("REPLACE_TARGET_ARCHIVE");

    String REPLACE_TARGET_SQL = properties.getProperty("REPLACE_TARGET_SQL");

    String REPLACE_SUCCESS_SQL = properties.getProperty("REPLACE_SUCCESS_SQL");

    String REPLACE_FAIL_SQL = properties.getProperty("REPLACE_FAIL_SQL");

    ConcurrentLinkedQueue<ReplaceTargetDto> replaceTargetQueue = new ConcurrentLinkedQueue<>();

}
