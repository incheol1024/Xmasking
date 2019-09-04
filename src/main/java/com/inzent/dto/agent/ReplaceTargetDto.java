package com.inzent.dto.agent;

public class ReplaceTargetDto {

    String m_sys_id;

    String if_img_dir;

    public String getM_sys_id() {
        return m_sys_id;
    }

    public void setM_sys_id(String m_sys_id) {
        this.m_sys_id = m_sys_id;
    }

    public String getIf_img_dir() {
        return if_img_dir;
    }

    public void setIf_img_dir(String if_img_dir) {
        this.if_img_dir = if_img_dir;
    }

    @Override
    public String toString() {
        return "ReplaceTargetDto{" +
                "m_sys_id='" + m_sys_id + '\'' +
                ", if_img_dir='" + if_img_dir + '\'' +
                '}';
    }
}
