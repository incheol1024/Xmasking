package com.inzent.initialize.database;

import com.inzent.initialize.Initializer;

public interface DatabaseInitializer extends Initializer {

    int DEFUALT_CONNECTION_COUNT = 10;

    enum EdmsTable {
        EDM_INFO_DET_T,
        EDM_INFO_DET_VER_T
    }

}
