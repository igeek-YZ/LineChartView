package com.igeek.linechartview;

/**
 * 坐标系点数据基类实体
 */
public class DataAixsPoint extends AixsPoint {
    //对应的轴数值
    private double aixsVal;
    //对应X轴的标题
    private String xAixsTitle;

    public double getAixsVal() {
        return aixsVal;
    }

    public void setAixsVal(double aixsVal) {
        this.aixsVal = aixsVal;
    }

    public String getxAixsTitle() {
        return xAixsTitle;
    }

    public void setxAixsTitle(String xAixsTitle) {
        this.xAixsTitle = xAixsTitle;
    }
}
