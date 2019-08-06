package com.inzent.dto.agent;

public class DownloadResultParamDto {

    //update xtorm.Ezs_XIF_TBL set if_stat = '12', if_img_dir = ?, if_down_etime = ?, if_reg_time = sysdate where m_sys_id = ?

    private int downloadResult;

    private String elementId;

    private String imgDir;

    private String downTime;


    public int getDownloadResult() {
        return downloadResult;
    }

    public void setDownloadResult(int downloadResult) {
        this.downloadResult = downloadResult;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }

    public String[] toParamArray() {
        String[] params = new String[3];
        params[0] = this.imgDir;
        params[1] = this.downTime;
        params[2] = this.elementId;
        return params;
    }


    //update xtorm.Ezs_XIF_TBL set if_stat = '12', if_img_dir = ?, if_down_etime = ?, if_reg_time = sysdate where m_sys_id = ?

}
