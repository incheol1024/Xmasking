package com.inzent.dic;

public enum ElementIdRange {

    EID_20("20%", "20", "29"),
    EID_30("30%", "30", "39"),
    EID_40("40%", "40", "49"),
    EID_50("50%", "50", "59"),
    EID_ED1("ED1%", "ED1", "ED1ZZZ"),
    EID_ED2("ED2%", "ED2", "ED2ZZZ"),
    EID_ED3("ED3%", "ED3", "ED4");

    private String elementIdRange;
    private String beginElementId;
    private String endElementId;

    ElementIdRange(String elementIdRange, String beginElementId, String endElementId) {
        this.elementIdRange = elementIdRange;
        this.beginElementId = beginElementId;
        this.endElementId = endElementId;
    }

    public String getElementIdRange() {
        return elementIdRange;
    }

    public String getBeginElementId() {
        return beginElementId;
    }

    public String getEndElementId() {
        return endElementId;
    }


}
