package com.inzent.dto.edms;

public class EdmsLookupDto {

    private String dcm_id;

    private String reg_date;

    public String getDcm_id() {
        return dcm_id;
    }

    public void setDcm_id(String dcm_id) {
        this.dcm_id = dcm_id;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return "EdmsLookupDto{" +
                "dcm_id='" + dcm_id + '\'' +
                ", reg_date='" + reg_date + '\'' +
                '}';
    }


}
