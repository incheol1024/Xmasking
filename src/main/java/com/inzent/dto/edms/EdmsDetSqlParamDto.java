package com.inzent.dto.edms;

public class EdmsDetSqlParamDto extends EdmsCommonSqlParamDto{


    @Override
    public String getMask_order() {
        return super.getMask_order();
    }

    @Override
    public void setMask_order(String mask_order) {
        super.setMask_order(mask_order);
    }

    @Override
    public String getTable_name() {
        return super.getTable_name();
    }

    @Override
    public void setTable_name(String table_name) {
        super.setTable_name(table_name);
    }

    @Override
    public String getLctg_cd() {
        return super.getLctg_cd();
    }

    @Override
    public void setLctg_cd(String lctg_cd) {
        super.setLctg_cd(lctg_cd);
    }

    @Override
    public String getMctg_cd() {
        return super.getMctg_cd();
    }

    @Override
    public void setMctg_cd(String mctg_cd) {
        super.setMctg_cd(mctg_cd);
    }

    @Override
    public String getForm_cd() {
        return super.getForm_cd();
    }

    @Override
    public void setForm_cd(String form_cd) {
        super.setForm_cd(form_cd);
    }

    @Override
    public String getDcm_id_begin() {
        return super.getDcm_id_begin();
    }

    @Override
    public void setDcm_id_begin(String dcm_id_begin) {
        super.setDcm_id_begin(dcm_id_begin);
    }

    @Override
    public String getDcm_id_end() {
        return super.getDcm_id_end();
    }

    @Override
    public void setDcm_id_end(String dcm_id_end) {
        super.setDcm_id_end(dcm_id_end);
    }

    @Override
    public String toString() {
        return "EdmsDetSqlParamDto{" +
                "mask_order='" + mask_order + '\'' +
                ", table_name='" + table_name + '\'' +
                ", lctg_cd='" + lctg_cd + '\'' +
                ", mctg_cd='" + mctg_cd + '\'' +
                ", form_cd='" + form_cd + '\'' +
                ", dcm_id_begin='" + dcm_id_begin + '\'' +
                ", dcm_id_end='" + dcm_id_end + '\'' +
                '}';
    }
}
