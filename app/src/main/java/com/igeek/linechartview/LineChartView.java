package com.igeek.linechartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igeek on 16/1/24.
 * author igeekcloud@gmail.com
 */
public class LineChartView extends View implements View.OnTouchListener {

    private static final String TAG = LineChartView.class.getSimpleName();

    //主要Y轴的节点集合
    private List<AixsPoint> yAixsPoints;
    //辅助Y轴(与主要Y轴位置相反)的节点集合
    private List<AixsPoint> yAuxAixsPoints;
    //X轴的节点集合
    private List<AixsPoint> xAixsPoints = new ArrayList<AixsPoint>(0);
    //折线图的列表
    private List<ChartLine> chartLines = new ArrayList<>(0);
    //参数
    private ChartAttrs ca;

    //Y轴的最大值
    private double maxYAixsVal;
    //Y轴的步值个数
    private int yAixsStepSize;

    //辅助Y轴的最大值
    private double maxYAuxAixsVal;

    //指定标准线的数据数值
    private int standardAixsVal;
    //虚线默认长度
    private int dashVal = 5;

    //坐标系基准线绘制X坐标
    private int xAixsOffset;
    //坐标系基准线绘制Y坐标
    private int yAixsOffset;

    //是否禁止触摸响应
    private boolean disableTouch=true;
    //是否显示垂直网格线
    private boolean showVerGridLine;
    //是否显示水平网格线
    private boolean showHorGridLine;
    //坐标轴是否和网格线对齐
    private boolean isAixsLineAlign;
    //是否隐藏y轴标题
    private boolean hideYAixsTitles;
    //是否隐藏辅助y轴标题
    private boolean showYAuxAixsTitles;
    //是否显示指定标准线
    private boolean showStandardLine;
    //是否显示指定标准值
    private boolean showStandardVal;
    //指定标准线的样式
    private int standardLineStyle=DASH;

    //x轴的位置 -top or bottom
    private int xAixsPostion=AIXS_BUTTOM;
    //y轴的位置 -left or right
    private int yAixsPostion=AIXS_LEFT;
    //手指触摸Path路径上对应的点到垂直轴的指示先的方向(Y轴或辅助Y轴) --左边或者右边
    private int pathPointToYAixsPosion=AIXS_LEFT;

    //double的精确度
    private int digit = 2;

    public static final int AIXS_LEFT = 0;
    public static final int AIXS_TOP = 1;
    public static final int AIXS_RIGHT = 2;
    public static final int AIXS_BUTTOM = 3;

    public static final int STROKE = 4;
    public static final int FILL = 5;
    public static final int FILL_STROKE = 6;
    public static final int STROKE_DASH = 7;
    public static final int DASH = 8;
    public static final int SOLID = 9;

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

    private onTouchAixsDataListener listener;

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleConfig(context, attrs);
        setOnTouchListener(this);
    }

    public void initStyleConfig(Context context, AttributeSet attrs) {
        ca = new ChartAttrs();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        hideYAixsTitles = ta.getBoolean(R.styleable.LineChartView_hideYAixsTitles, false);
        showVerGridLine = ta.getBoolean(R.styleable.LineChartView_showVerGridLine, true);
        showHorGridLine = ta.getBoolean(R.styleable.LineChartView_showHorGridLine, true);
        isAixsLineAlign = ta.getBoolean(R.styleable.LineChartView_isAixsLineAlign, false);
        xAixsPostion = ta.getInt(R.styleable.LineChartView_xAixsPostion, AIXS_BUTTOM);
        yAixsPostion = ta.getInt(R.styleable.LineChartView_yAixsPostion, AIXS_LEFT);
        standardLineStyle = ta.getInt(R.styleable.LineChartView_standardLineStyle, DASH);
        ca.setxAixsColor(ta.getColor(R.styleable.LineChartView_xAixsColor, Color.GRAY));
        ca.setyAixsColor(ta.getColor(R.styleable.LineChartView_yAixsColor, Color.GRAY));
        ca.setHorLineColor(ta.getColor(R.styleable.LineChartView_horLineColor, ca.getxAixsColor()));
        ca.setVerLineColor(ta.getColor(R.styleable.LineChartView_verLineColor, ca.getyAixsColor()));
        ca.setxAixsTitleColor(ta.getColor(R.styleable.LineChartView_xAixsTitleColor, ca.getxAixsColor()));
        ca.setyAixsTitleColor(ta.getColor(R.styleable.LineChartView_yAixsTitleColor, ca.getyAixsColor()));
        ca.setStandardLineColor(ta.getColor(R.styleable.LineChartView_standardLineColor, ca.getxAixsColor()));
        ca.setStandardValColor(ta.getColor(R.styleable.LineChartView_standardValColor, Color.RED));

        ca.setxAixsWidth(ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsWidth, 1));
        ca.setyAixsWidth(ta.getDimensionPixelSize(R.styleable.LineChartView_yAixsWidth, 1));

        ca.setxAixsTitleSize(ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsTitleSize, 14));
        ca.setyAixsTitleSize(ta.getDimensionPixelSize(R.styleable.LineChartView_yAixsTitleSize, 14));

        ca.setLineWidth(ta.getDimensionPixelSize(R.styleable.LineChartView_lineWidth, 1));
        ca.setStandardLineWidth(ta.getDimensionPixelSize(R.styleable.LineChartView_standardLineWidth, 0));
        ca.setStandardValSize(ta.getDimensionPixelSize(R.styleable.LineChartView_standardValSize, 16));
        ca.setStandardValMargin(ta.getDimensionPixelSize(R.styleable.LineChartView_standardValMargin, 8));

        ca.setxAixsPadding(ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsPadding, 0));
        ca.setyAixsPadding(ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsPadding, 0));

        ca.setxAixsPaddingTop(ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsPaddingTop, -1));
        ca.setxAixsPaddingBottom(ta.getDimensionPixelSize(R.styleable.LineChartView_xAixsPaddingBottom, -1));

        if (ca.getxAixsPaddingTop() == -1) ca.setxAixsPaddingTop(ca.getxAixsPadding());
        if (ca.getxAixsPaddingBottom() == -1) ca.setxAixsPaddingBottom(ca.getxAixsPadding());

        ca.setyAixsPaddingLeft(ta.getDimensionPixelSize(R.styleable.LineChartView_yAixsPaddingLeft, -1));
        ca.setyAixsPaddingRight(ta.getDimensionPixelSize(R.styleable.LineChartView_yAixsPaddingRight, -1));

        if (ca.getyAixsPaddingLeft() == -1) ca.setyAixsPaddingLeft(ca.getyAixsPadding());
        if (ca.getyAixsPaddingRight() == -1) ca.setyAixsPaddingRight(ca.getyAixsPadding());

        ta.recycle();

        aixsPaint = new Paint();
        aixsPaint.setAntiAlias(true);
        aixsPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ca.getHorLineColor());
        linePaint.setStrokeWidth(ca.getLineWidth());
        linePaint.setStyle(Paint.Style.STROKE);

        aixsTitlePaint = new Paint();
        aixsTitlePaint.setAntiAlias(true);
        aixsTitlePaint.setColor(ca.getxAixsTitleColor());
        aixsTitlePaint.setTextSize(ca.getxAixsTitleSize());
        aixsTitlePaint.setStyle(Paint.Style.STROKE);


        pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setStyle(Paint.Style.FILL);

        pathStrokePaint = new Paint();
        pathStrokePaint.setAntiAlias(true);
        pathStrokePaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener((l instanceof LineChartView) ? l : null);
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

        yAixsPoints = updateYAixsPoint(maxYAixsVal, yAixsStepSize);

        if (xAixsPoints.size() <= 0 || yAixsPoints.size() <= 0) {
            return;
        }
        yAuxAixsPoints = updateYAixsPoint(maxYAuxAixsVal, yAixsStepSize);

        int maxyAuxAixsTitleWidth = calculateMaxTitleWidth(yAuxAixsPoints);

        int yAixsStepHeight = measureHeight / yAixsPoints.size();
        int xAixsStepWidth = calculateXAixsStep(measureWidth, maxyAuxAixsTitleWidth);

        updateAixsOffset(measureWidth, measureHeight, xAixsStepWidth);

        //计算Y轴的标题位置和坐标
        for (int index = 0; index < yAixsPoints.size(); index++) {
            AixsPoint yaisPoint = yAixsPoints.get(index);
            int x = yAixsPostion == AIXS_RIGHT ? xAixsOffset + (measureWidth - xAixsOffset) / 2 : xAixsOffset / 2;
            int y = xAixsPostion == AIXS_TOP ? (yAixsOffset + index * yAixsStepHeight) : (yAixsOffset - index * yAixsStepHeight);
            int width = yAixsPostion == AIXS_RIGHT ? (measureWidth - xAixsOffset) : xAixsOffset;
            yaisPoint.setCenterY(y);
            yaisPoint.setCenterX(x);
            yaisPoint.setTitleWidth(width);
            yaisPoint.setTitleHeight(yAixsStepHeight);
        }

        //计算辅助Y轴的标题位置和坐标
        for (int index = 0; index < yAuxAixsPoints.size(); index++) {
            int maxTitleRange = maxyAuxAixsTitleWidth + ca.getyAixsPaddingLeft() + ca.getyAixsPaddingRight();
            AixsPoint yaisPoint = yAuxAixsPoints.get(index);
            int x = yAixsPostion == AIXS_RIGHT ? maxTitleRange / 2 + getPaddingLeft() : measureWidth - maxTitleRange + maxTitleRange / 2 - getPaddingRight();
            int y = xAixsPostion == AIXS_TOP ? (yAixsOffset + index * yAixsStepHeight) : (yAixsOffset - index * yAixsStepHeight);
            yaisPoint.setCenterY(y);
            yaisPoint.setCenterX(x);
            yaisPoint.setTitleWidth(maxTitleRange);
            yaisPoint.setTitleHeight(yAixsStepHeight);
        }

        //计算X轴的标题位置和坐标
        for (int index = 0; index < xAixsPoints.size(); index++) {
            AixsPoint xaisPoint = xAixsPoints.get(index);
            int x = yAixsPostion == AIXS_RIGHT ? (xAixsOffset - index * xAixsStepWidth) : xAixsOffset + index * xAixsStepWidth;
            int y = xAixsPostion == AIXS_TOP ? yAixsOffset / 2 : yAixsOffset + (measureHeight - yAixsOffset) / 2;
            int height = xAixsPostion == AIXS_TOP ? yAixsOffset : (measureHeight - yAixsOffset);
            xaisPoint.setCenterX(x);
            xaisPoint.setCenterY(y);
            xaisPoint.setTitleWidth(xAixsStepWidth);
            xaisPoint.setTitleHeight(height);
        }
        //更新数据的坐标准备绘制折线图或者区域图
        updateChartLinePos();
    }

    /**
     * 更新Y轴的数值和标题
     */
    public List<AixsPoint> updateYAixsPoint(double maxYAixsVal, int yAixsStepSize) {
        List<AixsPoint> points = new ArrayList<AixsPoint>();
        if (maxYAixsVal > 0 && yAixsStepSize > 0) {
            double yAixsStepVal = maxYAixsVal / yAixsStepSize;
            for (int index = 0; index <= yAixsStepSize; index++) {
                AixsPoint point = new AixsPoint();
                point.setTitle(String.valueOf(index * yAixsStepVal));
                points.add(point);
            }

        }
        return points;
    }

    /**
     * 计算坐标系X轴Y轴相对于本身视图(left,top)的偏移量
     */
    public void updateAixsOffset(int measureWidth, int measureHeight, int xAixsStepWidth) {

        //计算Y轴的X偏移量
        if (hideYAixsTitles) {
            xAixsOffset = yAixsPostion == AIXS_RIGHT ? measureWidth - getPaddingRight() - xAixsStepWidth / 2 : getPaddingLeft() + xAixsStepWidth / 2;
        } else {
            for (AixsPoint yaisPoint : yAixsPoints) {
                Rect rect = new Rect();
                aixsTitlePaint.setColor(ca.getyAixsTitleColor());
                aixsTitlePaint.setTextSize(ca.getyAixsTitleSize());
                aixsTitlePaint.getTextBounds(yaisPoint.getTitle(), 0, yaisPoint.getTitle().length(), rect);
                int offset = yAixsPostion == AIXS_RIGHT ? measureWidth - rect.width() - getPaddingRight() : rect.width() + getPaddingLeft();
                if (yAixsPostion == AIXS_RIGHT) {
                    if (xAixsOffset == 0 || xAixsOffset > offset - ca.getyAixsPaddingLeft() - ca.getyAixsPaddingRight())
                        xAixsOffset = offset - ca.getyAixsPaddingLeft() - ca.getyAixsPaddingRight();
                } else {
                    if (xAixsOffset < offset + ca.getyAixsPaddingLeft() + ca.getyAixsPaddingRight())
                        xAixsOffset = offset + ca.getyAixsPaddingLeft() + ca.getyAixsPaddingRight();
                }
            }
        }

        //计算X轴的Y偏移量
        for (AixsPoint xaisPoint : xAixsPoints) {
            Rect rect = new Rect();
            aixsTitlePaint.setColor(ca.getxAixsTitleColor());
            aixsTitlePaint.setTextSize(ca.getxAixsTitleSize());
            aixsTitlePaint.getTextBounds(xaisPoint.getTitle(), 0, xaisPoint.getTitle().length(), rect);
            int offset = xAixsPostion == AIXS_TOP ? getPaddingTop() + rect.height() : measureHeight - rect.height() - getPaddingBottom();
            if (xAixsPostion == AIXS_TOP) {
                if (yAixsOffset < offset + ca.getxAixsPaddingTop() + ca.getxAixsPaddingBottom())
                    yAixsOffset = offset + ca.getxAixsPaddingTop() + ca.getxAixsPaddingBottom();
            } else {
                if (yAixsOffset == 0 || yAixsOffset > offset - ca.getxAixsPaddingTop() - ca.getxAixsPaddingBottom())
                    yAixsOffset = offset - ca.getxAixsPaddingTop() - ca.getxAixsPaddingBottom();
            }
        }

    }

    /**
     * 更新和计算每条折线的位置
     */
    public void updateChartLinePos() {
        for (ChartLine chartLine : chartLines) {
            updateDataPointPos(chartLine.getDataPoints());
        }
    }

    /**
     * 更新和计算每个数据坐标的位置
     *
     * @param dataPoints 数据坐标集
     */
    public void updateDataPointPos(List<DataAixsPoint> dataPoints) {
        int ylastCenterY = yAixsPoints.get(yAixsPoints.size() - 1).getCenterY();
        int yfristCenterY = yAixsPoints.get(0).getCenterY();
        if (dataPoints.size() > 0 && xAixsPoints.size() >= dataPoints.size()) {
            for (int index = 0; index < dataPoints.size(); index++) {
                DataAixsPoint dataPoint = dataPoints.get(index);
                AixsPoint xPoint = serachAixs(dataPoint, xAixsPoints);
                if (xPoint == null) xPoint = xAixsPoints.get(index);
                dataPoint.setCenterX(xPoint.getCenterX());
                dataPoint.setCenterY(calculateCenterY(yfristCenterY, ylastCenterY, dataPoint.getAixsVal()));
            }
        }
    }

    /**
     * 计算X轴的步骤长
     */
    public int calculateXAixsStep(int measureWidth, int maxyAuxAixsTitleWidth) {
        if (hideYAixsTitles)
            return measureWidth / xAixsPoints.size();
        AixsPoint lastPoit = xAixsPoints.get(0);
        Rect rect = new Rect();
        aixsTitlePaint.setColor(ca.getxAixsTitleColor());
        aixsTitlePaint.setTextSize(ca.getxAixsTitleSize());
        aixsTitlePaint.getTextBounds(lastPoit.getTitle(), 0, lastPoit.getTitle().length(), rect);
        return (measureWidth - rect.width() / 2 - (showYAuxAixsTitles ? maxyAuxAixsTitleWidth + ca.getyAixsPaddingRight() + ca.getyAixsPaddingLeft() - rect.width() / 2 : 0) - getPaddingRight() - getPaddingLeft()) / xAixsPoints.size();

    }

    /**
     * 计算标题文本当中的最大文本宽度
     */
    public int calculateMaxTitleWidth(List<AixsPoint> aixsPoints) {
        int maxWidth = 0;
        for (AixsPoint aixsPoint : aixsPoints) {
            Rect rect = new Rect();
            aixsTitlePaint.setColor(ca.getyAixsTitleColor());
            aixsTitlePaint.setTextSize(ca.getyAixsTitleSize());
            aixsTitlePaint.getTextBounds(aixsPoint.getTitle(), 0, aixsPoint.getTitle().length(), rect);
            if (maxWidth < rect.width()) {
                maxWidth = rect.width();
            }
        }
        return maxWidth;
    }

    /**
     * 计算出每个节点在视图中Y轴的坐标
     *
     * @param yAixsFristCenterY Y轴的第一个节点(坐标轴起点)
     * @param yAixsLastCenterY  Y轴的最后一个节点
     * @param aixsVal           数据的数值
     * @return 根据数据的数值转化成对应的视图中Y轴的坐标
     */
    public int calculateCenterY(int yAixsFristCenterY, int yAixsLastCenterY, double aixsVal) {
        int yAixsDff = Math.abs(yAixsLastCenterY - yAixsFristCenterY);
        int top_centerY = (int) (yAixsFristCenterY + aixsVal * yAixsDff / maxYAixsVal);
        int bottom_centerY = (int) (yAixsLastCenterY + yAixsDff - aixsVal * yAixsDff / maxYAixsVal);
        return xAixsPostion == AIXS_TOP ? top_centerY : bottom_centerY;
    }

    /**
     * 检查是否X轴上对应的坐标
     *
     * @param dataPoint  目标数据坐标
     * @param aixsPoints X轴上的所有坐标
     * @return
     */
    public AixsPoint serachAixs(DataAixsPoint dataPoint, List<AixsPoint> aixsPoints) {
        for (AixsPoint aixsPoint : aixsPoints) {
            if (!TextUtils.isEmpty(dataPoint.getxAixsTitle()) &&
                    aixsPoint.getTitle().hashCode() == dataPoint.getxAixsTitle().hashCode()) {
                return aixsPoint;
            }
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (xAixsPoints.size() > 0 && yAixsPoints != null && yAixsPoints.size() > 0) {
            drawAixs(canvas);
            drawGridLine(canvas);
            drawChartLines(canvas);
            drawStandardLine(canvas);
            drawTouchLine(canvas);
        }
    }

    /**
     * 绘制坐标系
     */
    public void drawAixs(Canvas canvas) {

        if (xAixsPoints.size() <= 0 || yAixsPoints.size() <= 0) return;

        aixsTitlePaint.setColor(ca.getxAixsTitleColor());
        aixsTitlePaint.setTextSize(ca.getxAixsTitleSize());

        drawAixsPoints(canvas, xAixsPoints, aixsTitlePaint);

        if (!hideYAixsTitles) {
            aixsTitlePaint.setColor(ca.getyAixsTitleColor());
            aixsTitlePaint.setTextSize(ca.getyAixsTitleSize());

            drawAixsPoints(canvas, yAixsPoints, aixsTitlePaint);

            if (showYAuxAixsTitles) {
                drawAixsPoints(canvas, yAuxAixsPoints, aixsTitlePaint);
            }
        }

        if (!showHorGridLine) {
            //绘制Y轴
            aixsPaint.setColor(ca.getyAixsColor());
            aixsPaint.setStrokeWidth(ca.getyAixsWidth());
            AixsPoint lastYPoint = yAixsPoints.get(yAixsPoints.size() - 1);
            int targetY = xAixsPostion == AIXS_TOP ? (lastYPoint.getCenterY() + (isAixsLineAlign ? 0 : getHeight() / 2)) : (isAixsLineAlign ? lastYPoint.getCenterY() : 0);
            canvas.drawLine(xAixsOffset, yAixsOffset, xAixsOffset, targetY, aixsPaint);

            //Log.i(TAG, "Y轴（" + xAixsOffset + "," + yAixsOffset + ")-(" + xAixsOffset + "," + targetY + ")");
        }

        //绘制X轴
        aixsPaint.setColor(ca.getxAixsColor());
        aixsPaint.setStrokeWidth(ca.getxAixsWidth());
        AixsPoint lastXPoint = xAixsPoints.get(xAixsPoints.size() - 1);
        int startX = yAixsPostion == AIXS_RIGHT ? (isAixsLineAlign ? lastXPoint.getCenterX() : 0) : xAixsOffset;
        int targetX = yAixsPostion == AIXS_RIGHT ? xAixsOffset : (isAixsLineAlign ? lastXPoint.getCenterX() : getWidth());
        canvas.drawLine(startX, yAixsOffset, targetX, yAixsOffset, aixsPaint);

        //Log.i(TAG, "X轴（" + startX + "," + yAixsOffset + ")-(" + targetX + "," + yAixsOffset + ")");
    }

    /**
     * 绘制网格线
     */
    public void drawGridLine(Canvas canvas) {

        if (showVerGridLine && xAixsPoints.size() > 0) {
            //绘制Y轴的网格线
            linePaint.setColor(ca.getHorLineColor());
            linePaint.setStrokeWidth(ca.getLineWidth());
            for (AixsPoint xaisPoint : xAixsPoints) {
                canvas.drawLine(xaisPoint.getCenterX(), yAixsPoints.get(0).getCenterY(), xaisPoint.getCenterX(), yAixsPoints.get(yAixsPoints.size() - 1).getCenterY(), linePaint);
            }
        }

        if (showHorGridLine && yAixsPoints.size() > 0) {
            //绘制X轴的网格线
            linePaint.setColor(ca.getVerLineColor());
            for (AixsPoint yaisPoint : yAixsPoints) {
                canvas.drawLine(xAixsPoints.get(0).getCenterX(), yaisPoint.getCenterY(), xAixsPoints.get(xAixsPoints.size() - 1).getCenterX(), yaisPoint.getCenterY(), linePaint);
            }
        }

    }

    /**
     * 绘制所有的折线
     */
    public void drawChartLines(Canvas canvas) {
        for (ChartLine chartLine : chartLines) {
            drawDataPoint(canvas, chartLine);
        }
    }

    /**
     * 绘制指定的标准线
     */
    public void drawStandardLine(Canvas canvas) {

        int standardLineY = 0;
        if (showStandardLine && ca.getStandardLineWidth() > 0) {
            Path linePath = new Path();
            standardLineY = calculateCenterY(yAixsPoints.get(0).getCenterY(), yAixsPoints.get(yAixsPoints.size() - 1).getCenterY(), standardAixsVal);
            linePath.moveTo(xAixsPoints.get(0).getCenterX(), standardLineY);
            linePath.lineTo(xAixsPoints.get(xAixsPoints.size() - 1).getCenterX(), standardLineY);
            pathStrokePaint.setStyle(Paint.Style.STROKE);
            pathStrokePaint.setColor(ca.getStandardLineColor());
            pathStrokePaint.setStrokeWidth(ca.getStandardLineWidth());
            pathStrokePaint.setPathEffect(standardLineStyle == SOLID ? null : new DashPathEffect(new float[]{dashVal, dashVal}, 1));
            canvas.drawPath(linePath, pathStrokePaint);
        }

        if (showStandardLine && showStandardVal) {
            Rect rect = new Rect();
            String valStr = String.valueOf(standardAixsVal);
            aixsTitlePaint.setColor(ca.getStandardValColor());
            aixsTitlePaint.setTextSize(ca.getStandardValSize());
            aixsTitlePaint.getTextBounds(valStr, 0, valStr.length(), rect);
            if (Math.abs(standardLineY - yAixsPoints.get(yAixsPoints.size() - 1).getCenterY()) > rect.height() + ca.getStandardValMargin()) {
                int centerLeftX = (xAixsPoints.get(xAixsPoints.size() - 1).getCenterX() - xAixsOffset) / 2 + xAixsOffset;
                int centerRightY = (xAixsOffset - xAixsPoints.get(xAixsPoints.size() - 1).getCenterX()) / 2 + xAixsPoints.get(xAixsPoints.size() - 1).getCenterX();
                int centerY = xAixsPostion == AIXS_BUTTOM ? standardLineY - ca.getStandardValMargin() - rect.height() / 2 : standardLineY + ca.getStandardValMargin() + rect.height() / 2;
                AixsPoint tipPoint = new AixsPoint();
                tipPoint.setTitle(valStr);
                tipPoint.setCenterY(centerY);
                tipPoint.setCenterX(yAixsPostion == AIXS_LEFT ? centerLeftX : centerRightY);
                tipPoint.setTitleWidth(rect.width());
                tipPoint.setTitleHeight(rect.height());
                drawAixsPoint(tipPoint, canvas, aixsTitlePaint);
            } else {
                Log.i(TAG, "标准线与顶部空间不足,无法绘制指定值提示语");
            }
        }
    }

    /**
     * 绘制数据节点
     */
    public void drawDataPoint(Canvas canvas, ChartLine chartLine) {

        if (chartLine == null || chartLine.getDataPoints().size() == 0) return;

        pathPaint.setColor(chartLine.getFillColor());
        pathStrokePaint.setColor(chartLine.getLineColor());
        pathStrokePaint.setPathEffect(chartLine.getDrawModel() == STROKE_DASH ? new DashPathEffect(new float[]{dashVal, dashVal}, 1) : null);
        pathStrokePaint.setStrokeWidth(chartLine.getLineWidth());


        List<DataAixsPoint> dataPoints = chartLine.getDataPoints();

        Path path = new Path();
        Path strokePath = new Path();
        DataAixsPoint frstData = dataPoints.get(0);
        int tempFristCenterX = 0;
        if (frstData.getCenterX() == xAixsPoints.get(0).getCenterX()) {
            tempFristCenterX = xAixsPostion == AIXS_RIGHT ? frstData.getCenterX() - ca.getyAixsWidth() : frstData.getCenterX() + ca.getyAixsWidth();
        } else {
            tempFristCenterX = frstData.getCenterX();
        }
        path.moveTo(tempFristCenterX, frstData.getCenterY());
        strokePath.moveTo(tempFristCenterX, frstData.getCenterY());
        for (int index = 1; index < dataPoints.size(); index++) {
            DataAixsPoint datapoint = dataPoints.get(index);
            path.lineTo(datapoint.getCenterX(), datapoint.getCenterY());
            if (chartLine.getDrawModel() == STROKE)
                strokePath.lineTo(datapoint.getCenterX(), datapoint.getCenterY());
            else
                strokePath.lineTo(datapoint.getCenterX(), yAixsPostion == AIXS_TOP ? datapoint.getCenterY() + chartLine.getLineWidth() : datapoint.getCenterY() - chartLine.getLineWidth());
        }

        if (chartLine.getDrawModel() == STROKE || chartLine.getDrawModel() == STROKE_DASH) {
            canvas.drawPath(strokePath, pathStrokePaint);
        } else {
            DataAixsPoint lastData = dataPoints.get(dataPoints.size() - 1);
            int tempYAixsOffset = yAixsPostion == AIXS_TOP ? yAixsOffset + ca.getxAixsWidth() : yAixsOffset - ca.getxAixsWidth();
            path.lineTo(lastData.getCenterX(), tempYAixsOffset);
            path.lineTo(tempFristCenterX, tempYAixsOffset);
            path.lineTo(tempFristCenterX, frstData.getCenterY());
            canvas.drawPath(path, pathPaint);
            if (chartLine.getDrawModel() == FILL_STROKE)
                canvas.drawPath(strokePath, pathStrokePaint);
        }

    }

    /**
     * 绘制坐标轴标题节点集合
     */
    public void drawAixsPoints(Canvas canvas, List<AixsPoint> aisPoints, Paint paint) {
        for (AixsPoint yaisPoint : aisPoints) {
            drawAixsPoint(yaisPoint, canvas, paint);
        }
    }

    /**
     * 绘制单个坐标轴标题节点
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
     * 更新触摸对应的指示线
     */
    public void drawTouchLine(Canvas canvas) {
        if (downX > 0 && downY > 0&&xAixsPoints.size()>0) {
            linePaint.setColor(ca.getTouchLineColor());
            linePaint.setStrokeWidth(ca.getTouchLineWidth());
            int startX=downX - ca.getTouchLineWidth() / 2;
            int stratY=yAixsPoints.get(0).getCenterY()-ca.getxAixsWidth()/2;
            int stopY=yAixsPoints.get(yAixsPoints.size() - 1).getCenterY()-ca.getxAixsWidth()/2;
            //绘制垂直指示线
            canvas.drawLine(startX, stratY, startX, stopY, linePaint);
            //绘制水平指示线到特定的方向(左边或者右边)
            for(Double touchPathY:touchPathYs){
                final int yVal=touchPathY.intValue();
                int stopX=-1;
                if(yAixsPostion==AIXS_LEFT){
                    stopX=xAixsPoints.get(pathPointToYAixsPosion==AIXS_LEFT?0:xAixsPoints.size()-1).getCenterX();
                }else if(yAixsPostion==AIXS_RIGHT){
                    stopX=xAixsPoints.get(pathPointToYAixsPosion==AIXS_LEFT?xAixsPoints.size()-1:0).getCenterX();
                }
                if(stopX!=-1)
                    canvas.drawLine(startX,yVal,stopX,yVal,linePaint);
            }
        }else{
            //待定清除手指抬起来的指示线
//            if(downX!=-1&&downY!=-1){
//                downX=-1;
//                downY=-1;
//                invalidate();
//            }
        }
    }

    int downX;
    int downY;
    List<Double> touchPathYs;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (disableTouch) {
            return false;
        } else {
            if (inRange(event.getAction(), MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE)) {
                downX = (int) event.getX();
                downY = (int) event.getY();
            } else if (inRange(event.getAction(), MotionEvent.ACTION_UP)) {
                downX = -1;
                downY = -1;
                touchPathYs=null;
            }
            boolean touchInAixs = isTouchInAixs(downX, downY);
//            Log.i(TAG,"touchInAixs="+touchInAixs+" ;downX="+downX+" ; downY="+downY);
            if (touchInAixs&&downX>0&&downY>0){
                //计算用户指示触摸
                touchPathYs=touchYsBelongToPath(downX);
                if(touchPathYs.size()>0)
                    invalidate();
            }
            return touchInAixs;
        }
    }

    /**
     * 检查触摸点是否处理坐标系里
     */
    public boolean isTouchInAixs(int touchX, int touchY) {
        boolean inX = yAixsPostion == AIXS_LEFT ? inXAixs(touchX, 0, xAixsPoints.size() - 1) : inXAixs(touchX, xAixsPoints.size() - 1, 0);
        boolean inY = xAixsPostion == AIXS_TOP ? inYAixs(touchY, 0, yAixsPoints.size() - 1) : inYAixs(touchY, yAixsPoints.size() - 1, 0);
        return inX && inY;
    }

    public List<Double> touchYsBelongToPath(int touchX){

        List<Double> ys=new ArrayList<Double>();
        for(ChartLine chartLine:chartLines){
            double touchYInPathLine=getTouchYByAdjoin(touchX,chartLine.getDataPoints());
            if(touchYInPathLine!=-1){
                ys.add(touchYInPathLine);
            }
        }
        return ys;
    }

    /**
     * 计算得到用户手指获触摸所处当前坐标系的位置
     */
    public double getTouchYByAdjoin(int touchX,List<DataAixsPoint> dataPoints){

        int sreachIndex=-1;

        final int dataSize=dataPoints.size();

        for(int index=0;index<dataSize;index++){
            int checkIndex=yAixsPostion==AIXS_LEFT?index:dataSize-1-index;
            if(touchX<=dataPoints.get(checkIndex).getCenterX()){
                sreachIndex=checkIndex;
                break;
            }
        }

        if(yAixsPostion==AIXS_LEFT&&sreachIndex==0) return -1;
        if(yAixsPostion==AIXS_RIGHT&&sreachIndex==dataSize-1) return -1;

        AixsPoint two=dataPoints.get(sreachIndex);
        AixsPoint one=dataPoints.get(yAixsPostion==AIXS_LEFT?sreachIndex-1:sreachIndex+1);

//        Log.i(TAG,"TouchYByAdjoin -->one="+one.toString()+" ; two="+two.toString());

        //计算斜线的K值 y=kx+b
        double k=((double)(one.getCenterY()-two.getCenterY()))/(one.getCenterX()-two.getCenterX());
        //计算斜线的b值 y=kx+b
        double b=one.getCenterY()-one.getCenterX()*k;

//        Log.i(TAG,"touchX="+touchX+" ; K="+k+" ; b="+b);


        //求出touchy的位置
        return k*touchX+b;
    }

    public boolean inXAixs(int touchX, int startIndex, int endIndex) {
        return touchX > xAixsPoints.get(startIndex).getCenterX() && touchX < xAixsPoints.get(endIndex).getCenterX();
    }

    public boolean inYAixs(int touchY, int startIndex, int endIndex) {
        return touchY > yAixsPoints.get(startIndex).getCenterY() && touchY < yAixsPoints.get(endIndex).getCenterY();
    }

    public boolean inRange(int curAct, int... actions) {

        for (int index = 0; index < actions.length; index++) {
            if (curAct == actions[index])
                return true;
        }
        return false;
    }

    /**
     * 更新X轴的标题
     *
     * @param titles 标题集合
     */
    public LineChartView updateXAixsTitles(List<String> titles) {
        xAixsPoints.clear();
        for (String title : titles) {
            AixsPoint aixsPoint = new AixsPoint();
            aixsPoint.setTitle(title);
            xAixsPoints.add(aixsPoint);
        }
        return this;
    }

    /**
     * @param maxYAixsVal   Y轴的最大值
     * @param yAixsStepSize Y轴的步值个数
     */
    public LineChartView updateYAixsTitles(double maxYAixsVal, int yAixsStepSize) {
        this.maxYAixsVal = maxYAixsVal;
        this.yAixsStepSize = yAixsStepSize;
        return this;
    }

    public LineChartView updateYAuxAixsTitles(double maxYAuxAixsVal) {
        this.maxYAuxAixsVal = maxYAuxAixsVal;
        return this;
    }

    public LineChartView addChartLine(ChartLine chartLine) {
        if (chartLine != null)
            this.chartLines.add(chartLine);
        return this;
    }

    /**
     * 更新操作
     */
    public void drawToUpdate() {
        xAixsOffset = 0;
        yAixsOffset = 0;
        requestLayout();
        invalidate();
    }

    /**
     * 清除旧的折线数据集
     */
    public void cleanOldLines() {
        if (chartLines.size() > 0)
            chartLines.clear();
        if(touchPathYs!=null&&touchPathYs.size()>0)
            touchPathYs.clear();
    }

    public void setShowVerGridLine(boolean showVerGridLine) {
        this.showVerGridLine = showVerGridLine;
    }

    public void setShowHorGridLine(boolean showHorGridLine) {
        this.showHorGridLine = showHorGridLine;
    }

    public void setAixsLineAlign(boolean aixsLineAlign) {
        isAixsLineAlign = aixsLineAlign;
    }

    public void setHideYAixsTitles(boolean hideYAixsTitles) {
        this.hideYAixsTitles = hideYAixsTitles;
    }

    public int getxAixsPostion() {
        return xAixsPostion;
    }

    public void setxAixsPostion(int xAixsPostion) {
        this.xAixsPostion = xAixsPostion;
    }

    public int getyAixsPostion() {
        return yAixsPostion;
    }

    public void setyAixsPostion(int yAixsPostion) {
        this.yAixsPostion = yAixsPostion;
    }

    public boolean isShowStandardLine() {
        return showStandardLine;
    }

    public void setShowStandardLine(boolean showStandardLine) {
        this.showStandardLine = showStandardLine;
    }

    public int getStandardLineStyle() {
        return standardLineStyle;
    }

    public void setStandardLineStyle(int standardLineStyle) {
        this.standardLineStyle = standardLineStyle;
    }

    public int getStandardAixsVal() {
        return standardAixsVal;
    }

    public void setStandardAixsVal(int standardAixsVal) {
        this.standardAixsVal = standardAixsVal;
    }

    public int getDashVal() {
        return dashVal;
    }

    public ChartAttrs getChartAttrs() {
        return ca;
    }

    public void setChartAttrs(ChartAttrs ca) {
        this.ca = ca;
    }

    public void setDashVal(int dashVal) {
        this.dashVal = dashVal;
    }

    public boolean isShowStandardVal() {
        return showStandardVal;
    }

    public void setShowStandardVal(boolean showStandardVal) {
        this.showStandardVal = showStandardVal;
    }

    public boolean isShowYAuxAixsTitles() {
        return showYAuxAixsTitles;
    }

    public void setShowYAuxAixsTitles(boolean showYAuxAixsTitles) {
        this.showYAuxAixsTitles = showYAuxAixsTitles;
    }

    public int getPathPointToYAixsPosion() {
        return pathPointToYAixsPosion;
    }

    public void setPathPointToYAixsPosion(int pathPointToYAixsPosion) {
        this.pathPointToYAixsPosion = pathPointToYAixsPosion;
    }

    public boolean isDisableTouch() {
        return disableTouch;
    }

    public void setDisableTouch(boolean disableTouch) {
        this.disableTouch = disableTouch;
    }

    public onTouchAixsDataListener getListener() {
        return listener;
    }

    public void setListener(onTouchAixsDataListener listener) {
        this.listener = listener;
    }

    public static interface onTouchAixsDataListener {
        void onTouchAixsData(int xAixsTitle, int yAixsTitle, int yAuxAixsTitle);
    }
}
