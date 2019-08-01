package com.inzent.dto;

public class SqlParamDto {

    private String maskOrder;

    private String lctgCd;

    private String mctgCd;

    private String formCd;

    private String dcmIdBegin;

    private String dcmIdEnd;

    private String version;

    public String getMaskOrder() {
        return maskOrder;
    }

    public void setMaskOrder(String maskOrder) {
        this.maskOrder = maskOrder;
    }

    public String getLctgCd() {
        return lctgCd;
    }

    public void setLctgCd(String lctgCd) {
        this.lctgCd = lctgCd;
    }

    public String getMctgCd() {
        return mctgCd;
    }

    public void setMctgCd(String mctgCd) {
        this.mctgCd = mctgCd;
    }

    public String getFormCd() {
        return formCd;
    }

    public void setFormCd(String formCd) {
        this.formCd = formCd;
    }

    public String getDcmIdBegin() {
        return dcmIdBegin;
    }

    public void setDcmIdBegin(String dcmIdBegin) {
        this.dcmIdBegin = dcmIdBegin;
    }

    public String getDcmIdEnd() {
        return dcmIdEnd;
    }

    public void setDcmIdEnd(String dcmIdEnd) {
        this.dcmIdEnd = dcmIdEnd;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SqlParamDto{" +
                "maskOrder='" + maskOrder + '\'' +
                ", lctgCd='" + lctgCd + '\'' +
                ", mctgCd='" + mctgCd + '\'' +
                ", formCd='" + formCd + '\'' +
                ", dcmIdBegin='" + dcmIdBegin + '\'' +
                ", dcmIdEnd='" + dcmIdEnd + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
