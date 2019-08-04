package com.inzent.dic;

public enum ElementIdRange {

    EID_20("20%"),
    EID_30("30%"),
    EID_40("40%"),
    EID_50("50%"),
    EID_ED1("ED1%"),
    EID_ED2("ED2%"),
    EID_ED3("ED3%");

    private String elementIdRange;

    ElementIdRange(String elementIdRange) {
        this.elementIdRange = elementIdRange;
    }

    public String getElementIdRange() {
        return elementIdRange;
    }
}
