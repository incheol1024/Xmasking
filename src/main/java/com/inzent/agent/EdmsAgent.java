package com.inzent.agent;

public interface EdmsAgent extends Agent{

    enum EdmsFindMode {

        ALL,
        MASK_TARGET
    }

    enum  EdmsTableName {

        EDM_INFO_DET_T,
        EDM_INFO_DET_VER_T
    }
}
