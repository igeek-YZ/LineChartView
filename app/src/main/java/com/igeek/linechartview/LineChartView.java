package com.igeek.linechartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igeek on 16/1/24.
 */
public class LineChartView extends View {

    private List<AixsPoint> yAixsPoints;
    private List<AixsPoint> xAixsPoints=new ArrayList<AixsPoint>(0);
    private List<DataAixsPoint> dataPoints = new ArrayList<DataAixsPoint>(0);

    //x轴的颜色
    private int xAixsColor;
    //Y轴的颜色
    private int yAixsColor;
    //水平方向网格线的颜色
    private int horLineColor;
    //垂直方向网格线的颜色
    private int verLineColor;

    //x轴的标题字体颜色
    private int xAixsTitleColor;
    //y轴的标题字体颜色
    private int yAixsTitleColor;

    //路径区域填充颜色
    private int pathColor;
    //路径区域边框颜色
    private int pathStrokeColor;

    //坐标系的宽度
    private int aixsWidth;
    //网格线的宽度
    private int lineWidth;
    //路径边框宽度
    private int pathStrokeWidth;

    //x轴的标题字体大小
    private int xAixsTitleSize;
    //y轴的标题字体大小
    private int yAixsTitleSize;

    private int xAixsPadding;
    private int yAixsPadding;

    //x轴的标题上填充间距
    private int xAixsPaddingTop;
    //x轴的标题底部填充间距
    private int xAixsPaddingBottom;

    //y轴的标题左填充间距
    private int yAixsPaddingLeft;
    //y轴的标题右填充间距
    private int yAixsPaddingRight;

    //坐标系的画笔
    private Paint aixsPaint;
    //坐标系标题的画笔
    private Paint aixsTitlePaint;
    //网格线的画笔
    private Paint linePaint;
    //填充路径画笔
    private Paint pathPaint;
    //填充边框画笔
    private Paint pathStrokePaint;

    //坐标系基准线绘制X坐标
    private int xAixsOffset;
    //坐标系基准线绘制Y坐标
    private int yAixsOffset;

    //是否显示垂直网格线
    private boolean showVerGridLine;
    //是否显示水平网格线
    private boolean showHorGridLine;

    //x轴的位置 -left or right
    private int xAixsPostion;
    //y轴的位置 -top or bottom
    private int yAixsPostion;
    //绘制路径的模式
    private int pathModel;

    //double的精确度
    private int digit=2;

    private static final int AIXS_LEFT = 0;
    private static final int AIXS_TOP = 1;
    private static final int AIXS_RIGHT = 2;
    private static final int AIXS_BUTTOM = 3;

    private static final int PATH_STROKE = 4;
    private static final int PATH_FILL = 5;
    private static final int PATH_FILL_STROKE = 6;

    //Y轴的最大值
    private double maxYAixsVal;
    //Y轴的步值个数
    private int yAixsStepSize;

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleConfig(context, attrs);
    }

    public void initStyleConfig(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        showVerGridLine = ta.getBoolean(R.styleable.LineChartView_showVerGridLine, true);
        showHorGridLine = ta.getBoolean(R.styleable.LineChartView_showHorGridLine, true);
        pathColor = ta.getColor(R.styleable.LineChartView_pathColor, Color.GRAY);
        pathStrokeColor = ta.getColor(R.styleable.LineChartView_pathStrokeColor, Color.GRAY);
        xAixsColor = ta.getColor(R.styleable.LineChartView_xAixsColor, Color.GRAY);
        yAixsColor = ta.getColor(R.styleable.LineChartView_yAixsColor, Color.GRAY);
        horLineColor = ta.getColor(R.styleable.LineChartView_horLineColor, xAixsColor);
        verLineColor = ta.getColor(R.styleable.LineChartView_verLineColor, yAixsColor);
        xAixsTitleColor = ta.getColor(R.styleable.LineChartView_xAixsTitleColor, xAixsColor);
        yAixsTitleColor = ta.getColor(R.styleable.LineChartView_yAixsTitleColor, yAixsColor);
        xAixsPostion = ta.getInt(R.styleable.LineChartView_xAixsPostion, AIXS_LEFT);
        yAixsPostion = ta.getInt(R.styleable.LineChartView_yAixsPostion, AIXS_BUTTOM);
        pathModel = ta.getInt(R.styleable.LineChartView_pathModel, PATH_STROKE);
        aixsWidth = ta.getDimensionPixelSize(R.styleable.LineChartView_aixsWidth, 1);
        lineWidth = ta.getDimensionPixelSize(R.styleable.LineChartView_lineWidth, 1);
        pathStrokeWidth = ta.getDimensionPixelSize(R.styleable.LineChartView_pathStrokeWidth, 0);
        xAixsTitleSize = ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsTitleSize, 14);
        yAixsTitleSize = ta.getDimensionPixelSize(R.styleable.LineChartView_yAixsTitleSize, 14);

        xAixsPadding = ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsPadding, 0);
        yAixsPadding = ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsPadding, 0);

        xAixsPaddingTop = ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsPaddingTop, -1);
        xAixsPaddingBottom = ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsPaddingBottom, -1);

        if (xAixsPaddingTop == -1) xAixsPaddingTop = xAixsPadding;
        if (xAixsPaddingBottom == -1) xAixsPaddingBottom = xAixsPadding;

        yAixsPaddingLeft = ta.getDimensionPixelSize(R.styleable.LineChartView_yAixsPaddingLeft, -1);
        yAixsPaddingRight = ta.getDimensionPixelSize(R.styleable.LineChartView_yAixsPaddingRight, -1);

        if (yAixsPaddingLeft == -1) yAixsPaddingLeft = yAixsPadding;
        if (yAixsPaddingRight == -1) yAixsPaddingRight = yAixsPadding;

        ta.recycle();

        aixsPaint = new Paint();
        aixsPaint.setAntiAlias(true);
        aixsPaint.setColor(xAixsColor);
        aixsPaint.setStrokeWidth(aixsWidth);
        aixsPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(horLineColor);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStyle(Paint.Style.STROKE);

        aixsTitlePaint = new Paint();
        aixsTitlePaint.setAntiAlias(true);
        aixsTitlePaint.setColor(xAixsTitleColor);
        aixsTitlePaint.setTextSize(xAixsTitleSize);
        aixsTitlePaint.setStyle(Paint.Style.STROKE);

        if (pathModel == PATH_FILL_STROKE) {

            pathPaint = new Paint();
            pathPaint.setAntiAlias(true);
            pathPaint.setColor(pathColor);
            pathPaint.setStyle(Paint.Style.FILL);

            pathStrokePaint = new Paint();
            pathStrokePaint.setAntiAlias(true);
            pathStrokePaint.setColor(pathStrokeColor);
            pathStrokePaint.setStrokeWidth(pathStrokeWidth);
            pathStrokePaint.setStyle(Paint.Style.STROKE);

        } else if (pathModel == PATH_FILL) {
            pathPaint = new Paint();
            pathPaint.setAntiAlias(true);
            pathPaint.setColor(pathColor);
            pathPaint.setStyle(Paint.Style.FILL);
        } else {
            pathStrokePaint = new Paint();
            pathStrokePaint.setAntiAlias(true);
            pathStrokePaint.setColor(pathStrokeColor);
            pathStrokePaint.setStrokeWidth(pathStrokeWidth);
            pathStrokePaint.setStyle(Paint.Style.STROKE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST)
            widthSize = 0;
        if (heightMode == MeasureSpec.AT_MOST)
            heightSize = 0;
        if (widthSize != 0 && heightSize != 0)
            reSetUpdateAxis(widthSize, heightSize);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 重新计算坐标系的位置和坐标轴偏移量
     */
    public void reSetUpdateAxis(int measureWidth, int measureHeight) {

        yAixsPoints = updateYAixsPoint();

        if (xAixsPoints.size() <= 0 || yAixsPoints.size() <= 0) {
            return;
        }

        updateAixsOffset(measureWidth, measureHeight);

        int yAixsStepHeight = measureHeight / yAixsPoints.size();
        int xAixsStepWidth = (measureWidth-xAixsPoints.get(xAixsPoints.size()-1).getTitleWidth()/2) / xAixsPoints.size();

        //计算Y轴的标题位置和坐标
        for (int index = 0; index < yAixsPoints.size(); index++) {
            AixsPoint yaisPoint = yAixsPoints.get(index);
            int x = xAixsPostion == AIXS_RIGHT ? xAixsOffset + (measureWidth - xAixsOffset) / 2 : xAixsOffset / 2;
            int y = yAixsPostion == AIXS_TOP ? (yAixsOffset + index * yAixsStepHeight) : (yAixsOffset - index * yAixsStepHeight);
            int width = xAixsPostion == AIXS_RIGHT ? (measureWidth - xAixsOffset) : xAixsOffset;
            yaisPoint.setCenterY(y);
            yaisPoint.setCenterX(x);
            yaisPoint.setTitleWidth(width);
            yaisPoint.setTitleHeight(yAixsStepHeight);
        }

        //计算X轴的标题位置和坐标
        for (int index = 0; index < xAixsPoints.size(); index++) {
            AixsPoint xaisPoint = xAixsPoints.get(index);
            int x = xAixsPostion == AIXS_RIGHT ? (xAixsOffset - index * xAixsStepWidth) : xAixsOffset + index * xAixsStepWidth;
            int y = yAixsPostion == AIXS_TOP ? yAixsOffset / 2 : yAixsOffset + (measureHeight - yAixsOffset) / 2;
            int height = yAixsPostion == AIXS_TOP ? yAixsOffset : (measureHeight - yAixsOffset);
            xaisPoint.setCenterX(x);
            xaisPoint.setCenterY(y);
            xaisPoint.setTitleWidth(xAixsStepWidth);
            xaisPoint.setTitleHeight(height);
        }
        //更新数据的坐标准备绘制折线图或者区域图
        updateDataPointPos();
    }

    /**
     * 更新Y轴的数值和标题
     */
    public List<AixsPoint> updateYAixsPoint() {
        List<AixsPoint> points = new ArrayList<AixsPoint>();
        if (maxYAixsVal > 0 && yAixsStepSize > 0) {
            double yAixsStepVal = douFormat(maxYAixsVal / yAixsStepSize,digit);
            for (int index = 0; index <= yAixsStepSize; index++) {
                AixsPoint point = new AixsPoint();
                point.setTitle(String.valueOf(index * yAixsStepVal));
                points.add(point);
            }

        }
        return points;
    }

    /**
     * 计算坐标系X轴Y轴相对于视图(left,top)的偏移量
     */
    public void updateAixsOffset(int measureWidth, int measureHeight) {

        //计算Y轴的X偏移量
        for (AixsPoint yaisPoint : yAixsPoints) {
            Rect rect = new Rect();
            aixsTitlePaint.setColor(yAixsTitleColor);
            aixsTitlePaint.setTextSize(yAixsTitleSize);
            aixsTitlePaint.getTextBounds(yaisPoint.getTitle(), 0, yaisPoint.getTitle().length(), rect);
            int offset = xAixsPostion == AIXS_RIGHT ? measureWidth - rect.width() - getPaddingRight() : rect.width() + getPaddingLeft();
            if (xAixsPostion == AIXS_RIGHT) {
                if (xAixsOffset == 0 || xAixsOffset > offset - yAixsPaddingLeft - yAixsPaddingRight)
                    xAixsOffset = offset - yAixsPaddingLeft - yAixsPaddingRight;
            } else {
                if (xAixsOffset < offset + yAixsPaddingLeft + yAixsPaddingRight)
                    xAixsOffset = offset + yAixsPaddingLeft + yAixsPaddingRight;
            }
        }

        //计算X轴的Y偏移量
        for (AixsPoint xaisPoint : xAixsPoints) {
            Rect rect = new Rect();
            aixsTitlePaint.setColor(xAixsTitleColor);
            aixsTitlePaint.setTextSize(xAixsTitleSize);
            aixsTitlePaint.getTextBounds(xaisPoint.getTitle(), 0, xaisPoint.getTitle().length(), rect);
            int offset = yAixsPostion == AIXS_TOP ? getPaddingTop() + rect.height() : measureHeight - rect.height() - getPaddingBottom();
            if (yAixsPostion == AIXS_TOP) {
                if (yAixsOffset < offset + xAixsPaddingTop + xAixsPaddingBottom)
                    yAixsOffset = offset + xAixsPaddingTop + xAixsPaddingBottom;
            } else {
                if (yAixsOffset == 0 || yAixsOffset > offset - xAixsPaddingTop - xAixsPaddingBottom)
                    yAixsOffset = offset - xAixsPaddingTop - xAixsPaddingBottom;
            }
        }
    }

    /**
     * 更新数据集中每个点的位置
     */
    public void updateDataPointPos() {
        int ylastCenterY = yAixsPoints.get(yAixsPoints.size() - 1).getCenterY();
        int yfristCenterY = yAixsPoints.get(0).getCenterY();
        int yAixsDff = Math.abs(ylastCenterY - yfristCenterY);
        if (dataPoints.size() > 0 && xAixsPoints.size() == dataPoints.size()) {
            for (int index = 0; index < dataPoints.size(); index++) {
                DataAixsPoint dataPoint = dataPoints.get(index);
                AixsPoint xPoint = xAixsPoints.get(index);
                dataPoint.setCenterX(xPoint.getCenterX());
                int top_centerY = (int) (yfristCenterY + dataPoint.getAixsVal() * yAixsDff / maxYAixsVal);
                int bottom_centerY = (int) (ylastCenterY + yAixsDff - dataPoint.getAixsVal() * yAixsDff / maxYAixsVal);
                dataPoint.setCenterY(yAixsPostion == AIXS_TOP ? top_centerY : bottom_centerY);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawXAixs(canvas);
        drawGridLine(canvas);
        drawDataPoint(canvas);
    }

    /**
     * 绘制坐标系
     */
    public void drawXAixs(Canvas canvas) {

        if(dataPoints.size()<=0) return;

        aixsTitlePaint.setColor(xAixsTitleColor);
        aixsTitlePaint.setTextSize(xAixsTitleSize);
        for (AixsPoint xaisPoint : xAixsPoints) {
            drawAixsPoint(xaisPoint, canvas, aixsTitlePaint);
        }

        aixsTitlePaint.setColor(yAixsTitleColor);
        aixsTitlePaint.setTextSize(yAixsTitleSize);
        for (AixsPoint yaisPoint : yAixsPoints) {
            drawAixsPoint(yaisPoint, canvas, aixsTitlePaint);
        }

        //绘制Y轴
        aixsPaint.setColor(yAixsColor);
        int targetY = yAixsPostion == AIXS_TOP ? (yAixsPoints.get(yAixsPoints.size() - 1).getCenterY() + getHeight() / 2) : 0;
        canvas.drawLine(xAixsOffset, yAixsOffset, xAixsOffset, targetY, aixsPaint);
        //绘制X轴
        aixsPaint.setColor(xAixsColor);
        int startX = xAixsPostion == AIXS_RIGHT ? 0 : xAixsOffset;
        int targetX = xAixsPostion == AIXS_RIGHT ? xAixsOffset : getWidth();
        canvas.drawLine(startX, yAixsOffset, targetX, yAixsOffset, aixsPaint);
    }

    /**
     * 绘制网格线
     *
     * @param canvas
     */
    public void drawGridLine(Canvas canvas) {

        if (showVerGridLine&&xAixsPoints.size()>0) {
            //绘制Y轴的网格线
            linePaint.setColor(horLineColor);
            for (AixsPoint xaisPoint : xAixsPoints) {
                canvas.drawLine(xaisPoint.getCenterX(), yAixsPoints.get(0).getCenterY(), xaisPoint.getCenterX(), yAixsPoints.get(yAixsPoints.size() - 1).getCenterY(), linePaint);
            }
        }

        if (showHorGridLine&&yAixsPoints.size()>0) {
            //绘制X轴的网格线
            linePaint.setColor(verLineColor);
            for (AixsPoint yaisPoint : yAixsPoints) {
                canvas.drawLine(xAixsPoints.get(0).getCenterX(), yaisPoint.getCenterY(), xAixsPoints.get(xAixsPoints.size() - 1).getCenterX(), yaisPoint.getCenterY(), linePaint);
            }
        }

    }

    /**
     * 绘制坐标轴标题节点
     */
    public void drawAixsPoint(AixsPoint aisPoint, Canvas canvas, Paint paint) {
        Rect targetRect = new Rect();
        targetRect.left = aisPoint.getCenterX() - aisPoint.getTitleWidth() / 2;
        targetRect.top = aisPoint.getCenterY() - aisPoint.getTitleHeight() / 2;
        targetRect.right = aisPoint.getCenterX() + aisPoint.getTitleWidth() / 2;
        targetRect.bottom = aisPoint.getCenterY() + aisPoint.getTitleHeight() / 2;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 实现水平居中，drawText对应改为传入targetRect.centerX(),也可以不设置，默认为left,自己计算
        paint.setTextAlign(Paint.Align.CENTER);
        //canvas.drawRect(targetRect,strokePaint);
        canvas.drawText(aisPoint.getTitle(), targetRect.centerX(), baseline, paint);
    }

    /**
     * 绘制数据节点
     */
    public void drawDataPoint(Canvas canvas) {

        if(dataPoints.size()==0) return ;

        Path path = new Path();
        Path strokePath = new Path();
        DataAixsPoint frstData = dataPoints.get(0);
        int tempFristCenterX=xAixsPostion == AIXS_RIGHT ?frstData.getCenterX()-aixsWidth:frstData.getCenterX()+aixsWidth;
        path.moveTo(tempFristCenterX, frstData.getCenterY());
        strokePath.moveTo(tempFristCenterX, frstData.getCenterY());
        for (int index = 1; index < dataPoints.size(); index++) {
            DataAixsPoint datapoint = dataPoints.get(index);
            path.lineTo(datapoint.getCenterX(), datapoint.getCenterY());
            if (pathModel == PATH_STROKE)
                strokePath.lineTo(datapoint.getCenterX(), datapoint.getCenterY());
            else
                strokePath.lineTo(datapoint.getCenterX(), yAixsPostion == AIXS_TOP ? datapoint.getCenterY() + pathStrokeWidth : datapoint.getCenterY() - pathStrokeWidth);
        }

        if (pathModel == PATH_STROKE) {
            canvas.drawPath(strokePath, pathStrokePaint);
        } else {
            DataAixsPoint lastData = dataPoints.get(dataPoints.size() - 1);
            int tempYAixsOffset=yAixsPostion == AIXS_TOP ?yAixsOffset+aixsWidth:yAixsOffset-aixsWidth;
            path.lineTo(lastData.getCenterX(), tempYAixsOffset);
            path.lineTo(tempFristCenterX, tempYAixsOffset);
            path.lineTo(tempFristCenterX, frstData.getCenterY());
            canvas.drawPath(path, pathPaint);
            if (pathModel == PATH_FILL_STROKE)
                canvas.drawPath(strokePath, pathStrokePaint);
        }
    }

    /**
     * 坐标系点坐标实体
     */
    public static class AixsPoint {
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
    }

    /**
     * 坐标系点数据基类实体
     */
    public static class DataAixsPoint extends AixsPoint {
        //对应的轴数值
        private double aixsVal;

        public double getAixsVal() {
            return aixsVal;
        }

        public void setAixsVal(double aixsVal) {
            this.aixsVal = aixsVal;
        }
    }

    /**
     * 更新X轴的标题
     * @param titles 标题集合
     */
    public LineChartView updateXAixsTitles(List<String> titles){
        xAixsPoints.clear();
        for (String title:titles) {
            AixsPoint aixsPoint = new AixsPoint();
            aixsPoint.setTitle(title);
            xAixsPoints.add(aixsPoint);
        }
        return this;
    }

    /**
     *
     * @param maxYAixsVal Y轴的最大值
     * @param yAixsStepSize Y轴的步值个数
     */
    public LineChartView updateYAixsTitles(double maxYAixsVal,int yAixsStepSize){
        this.maxYAixsVal=maxYAixsVal;
        this.yAixsStepSize=yAixsStepSize;
        return this;
    }

    /**
     * 更新数据集合
     * @param dataPoints 要显示的数据集
     */
    public LineChartView updateDataPoints(List<DataAixsPoint> dataPoints){
        this.dataPoints=dataPoints;
        return this;
    }

    /**
     * 更新操作
     */
    public void drawToUpdate(){
        requestLayout();
    }

    public static double douFormat(double value,int digit){
        NumberFormat format=NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(digit);
        return Double.parseDouble(format.format(value).replace(",",""));
    }

}
