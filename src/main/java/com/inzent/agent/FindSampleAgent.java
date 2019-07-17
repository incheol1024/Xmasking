package com.inzent.agent;

import com.inzent.db.SampleDao;

import java.util.List;

public class FindSampleAgent {

    public List<String> getElementIdList() {

        new SampleDao().countProduct();
        return null;
    }


}
