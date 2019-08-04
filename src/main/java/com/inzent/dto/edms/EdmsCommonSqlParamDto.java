package com.inzent.dto.edms;

public abstract class EdmsCommonSqlParamDto {

     String mask_order;

     String table_name;

     String lctg_cd;

     String mctg_cd;

     String form_cd;

     String dcm_id_begin;

     String dcm_id_end;


    public String getMask_order() {
        return mask_order;
    }

    public void setMask_order(String mask_order) {
        this.mask_order = mask_order;
    }

    public String gtTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getLctg_cd() {
        return lctg_cd;
    }

    public void setLctg_cd(String lctg_cd) {
        this.lctg_cd = lctg_cd;
    }

    public String getMctg_cd() {
        return mctg_cd;
    }

    public void setMctg_cd(String mctg_cd) {
        this.mctg_cd = mctg_cd;
    }

    public String getForm_cd() {
        return form_cd;
    }

    public void setForm_cd(String form_cd) {
        this.form_cd = form_cd;
    }

    public String getDcm_id_begin() {
        return dcm_id_begin;
    }

    public void setDcm_id_begin(String dcm_id_begin) {
        this.dcm_id_begin = dcm_id_begin;
    }

    public String getDcm_id_end() {
        return dcm_id_end;
    }

    public void setDcm_id_end(String dcm_id_end) {
        this.dcm_id_end = dcm_id_end;
    }
}
