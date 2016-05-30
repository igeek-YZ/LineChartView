package com.igeek.linechartview;

/**
 * 坐标系点坐标实体
 */
public class AixsPoint {
    //中心X坐标
    private int centerX;
    //中心Y坐标
    private int centerY;
    //标题文本的宽度
    private int titleWidth;
    //标题文本的高度
    private int titleHeight;
    //标题文本
    private String title;

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getTitleWidth() {
        return titleWidth;
    }

    public void setTitleWidth(int titleWidth) {
        this.titleWidth = titleWidth;
    }

    public int getTitleHeight() {
        return titleHeight;
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean eq(AixsPoint other){

        if(other!=null){
            boolean isX=centerX==other.getCenterX();
            boolean isY=centerY==other.getCenterY();
            boolean isTitle=title.hashCode()==other.getTitle().hashCode();
            return isX&&isY&&isTitle;
        }
        return false;
    }

    @Override
    public String toString() {
        return "AixsPoint{" +
                "title='" + title + '\'' +
                ", centerY=" + centerY +
                ", centerX=" + centerX +
                '}';
    }
}
