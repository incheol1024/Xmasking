package com.inzent.dto.mask;

public class DownTargetDto {

    String m_sys_id;

    String if_img_dir;

    public String getM_sys_id() {
        return m_sys_id;
    }

    public void setM_sys_id(String m_sys_id) {
        this.m_sys_id = m_sys_id;
    }

    @Override
    public String toString() {
        return "DownTargetDto{" +
                "m_sys_id='" + m_sys_id + '\'' +
                ", if_img_dir='" + if_img_dir + '\'' +
                '}';
    }
}
