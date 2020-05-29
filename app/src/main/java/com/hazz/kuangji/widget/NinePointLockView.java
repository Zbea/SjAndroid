package com.hazz.kuangji.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AmosShi on 2016/11/11.
 * <p>
 * Description: 自定义控件，九宫格应用解锁，已做好屏幕适配，直接在布局文件中写想要的大小即可
 * 监听：setOnDrawLickPathListener(OnDrawLockPathListener onDrawLickPathListener)
 * 回调接口： OnDrawLockPathListener
 * <p>
 * Email:shixiuwen1991@yeah.net
 * <p>
 * 分析：需要绘画层级：
 * 1.点层
 * 2.线层
 */

public class NinePointLockView extends FrameLayout {

    private Paint pointPaint;   //用于画点的paint
    private Paint linePaint;    //用户画线的paint

    private PointView pointView;
    private LineView lineView;

    private ArrayList<Integer> list;    //用于存储绘制的路径 {横向：0，1，2，3，4，5，6，7，8}
    private ArrayList<Integer> fingerPath = new ArrayList<>();  //临时回调的时候使用

    private boolean isListEmpty = true; //表示该区域是否已经连接上

    private float touchX, touchY;

    private int resultRule = 790; //onMeasure比例尺，用作绘制比例,790是初始值，最终会根据实际大小重新赋值

    private float ratioScale = 0;   //在原比例尺上的缩放比例

    private int measureWidth, measureHeight;

    private Float[][] localPoint;   //绘制的连接线坐标点位置

    private boolean isRightPath = true;
    private static boolean isFingerUp = true;   //之后手指抬起的时候才主动使用timer清除路径

    public NinePointLockView(Context context) {
//        super(context);
        this(context, null);
    }

    public NinePointLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        list = new ArrayList<>();
        //        initScare();
        pointView = new PointView(context, attrs);
        lineView = new LineView(context, attrs);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(pointView, layoutParams);
        addView(lineView, layoutParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN && list.size() > 0) {
            list.clear();
            isRightPath = true;
            isListEmpty = true;
            fingerPath.clear();
            isFingerUp = false;
        }

        int area = calculateArea(event.getX(), event.getY());
        if (area != -1) {   //如果当前手指按在了某个区域，则刷新控件开始画线
            if (!list.contains(area)) { //点的状态只刷新一次
                Log.i("event", "x:" + event.getX() + " " + "y:" + event.getY() + " " + "area:" + area);
                invalidateStatus(area);
            }
        }
        //移动过程中线的状态不断刷新
        touchX = event.getX();
        touchY = event.getY();
        lineView.invalidate();

        boolean isRight = true;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            ArrayList<Integer> gesturePathList = getGesturePath();//手指抬起时获取绘制的路径
            if (onDrawLockPathListener != null) {
                isRight = onDrawLockPathListener.onDrawPathFinish(gesturePathList);
            }
            if (isRight) {
                showPathAfterFingerUp(1000, true);
            } else {
                showPathAfterFingerUp(1000, false);
            }
        }
        return true;
    }

    /**
     * 手指抬起后将路径显示一段指定的时长，如果路径不正确（表示不匹配），则将路径显示为红色错误状态
     *
     * @param duration 路径显示时长，ms
     * @param isRight  路径是否错误，如果路径错误，则显示为红色
     */
    private void showPathAfterFingerUp(long duration, boolean isRight) {
        isRightPath = isRight;
        pointView.invalidate();
        lineView.invalidate();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isFingerUp) {
                    isRightPath = true;
                    isListEmpty = true;
                    list.clear();
                    pointView.postInvalidate();
                    lineView.postInvalidate();
                }
                isFingerUp = true;
            }
        };
        timer.schedule(timerTask, duration);
    }

    /**
     * 计算当前手指所在区域
     *
     * @param x x
     * @param y y
     * @return 区域标识
     */
    private int calculateArea(float x, float y) {
        int area = -1;
        if (x > 45 * ratioScale && x < 212 * ratioScale && y > 45 * ratioScale && y < 212 * ratioScale) {   //进入了第一个点的区域
            area = 0;
        } else if (x > 308 * ratioScale && x < 474 * ratioScale && y > 45 * ratioScale && y < 212 * ratioScale) {
            area = 1;
        } else if (x > 566 * ratioScale && x < 738 * ratioScale && y > 45 * ratioScale && y < 212 * ratioScale) {
            area = 2;
        } else if (x > 45 * ratioScale && x < 212 * ratioScale && y > 308 * ratioScale && y < 474 * ratioScale) {
            area = 3;
        } else if (x > 308 * ratioScale && x < 474 * ratioScale && y > 308 * ratioScale && y < 474 * ratioScale) {
            area = 4;
        } else if (x > 566 * ratioScale && x < 738 * ratioScale && y > 308 * ratioScale && y < 474 * ratioScale) {
            area = 5;
        } else if (x > 45 * ratioScale && x < 212 * ratioScale && y > 566 * ratioScale && y < 738 * ratioScale) {
            area = 6;
        } else if (x > 308 * ratioScale && x < 474 * ratioScale && y > 566 * ratioScale && y < 738 * ratioScale) {
            area = 7;
        } else if (x > 566 * ratioScale && x < 738 * ratioScale && y > 566 * ratioScale && y < 738 * ratioScale) {
            area = 8;
        }
        return area;
    }

    /**
     * 根据手指区域刷新界面
     *
     * @param area 区域
     */
    private void invalidateStatus(int area) {
        if (isListEmpty) {
            list.add(area);
            list.add(100);    //区域点始终多一个，表示手指所在区域，用来连线
            isListEmpty = false;
        } else {
            list.add(list.size() - 1, area);
        }
        pointView.invalidate();
    }

    /**
     * 九宫格中的点视图
     */
    private class PointView extends View {

        public PointView(Context context) {
//            super(context);
            this(context, null);
        }

        public PointView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initPointPaint();
            post(new Runnable() {
                @Override
                public void run() {
                    localPoint = new Float[][]{{130 * ratioScale, 130 * ratioScale}, {395 * ratioScale, 130 * ratioScale}, {660 * ratioScale, 130 * ratioScale},
                            {130 * ratioScale, 395 * ratioScale}, {395 * ratioScale, 395 * ratioScale}, {660 * ratioScale, 395 * ratioScale},
                            {130 * ratioScale, 660 * ratioScale}, {395 * ratioScale, 660 * ratioScale}, {660 * ratioScale, 660 * ratioScale}};
                }
            });
        }

        /**
         * 初始化画点的画笔
         */
        private void initPointPaint() {
            pointPaint = new Paint();
            pointPaint.setARGB(255, 166, 166, 166);
            pointPaint.setAntiAlias(true);
            pointPaint.setDither(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //将画布移到布局空间的中间,防止布局为长方形
            canvas.translate(Math.abs(resultRule - measureWidth) / 2, Math.abs(resultRule - measureHeight) / 2);

            //转移画布中心点到中心
            canvas.translate(395 * ratioScale, 395 * ratioScale);

            //画中间的那个点
            drawPoint(0, 0, canvas, 4);
            //画左上角的点
            drawPoint((int) (-264 * ratioScale), (int) (-264 * ratioScale), canvas, 0);
            //画右上角的点
            drawPoint((int) (264 * ratioScale), (int) (-264 * ratioScale), canvas, 2);
            //画左下角的点
            drawPoint((int) (-264 * ratioScale), (int) (264 * ratioScale), canvas, 6);
            //画右下角的点
            drawPoint((int) (264 * ratioScale), (int) (264 * ratioScale), canvas, 8);
            //画上边的点
            drawPoint(0, (int) (-264 * ratioScale), canvas, 1);
            //画下边的点
            drawPoint(0, (int) (264 * ratioScale), canvas, 7);
            //画左边的点
            drawPoint((int) (-264 * ratioScale), 0, canvas, 3);
            //画右边的点
            drawPoint((int) (264 * ratioScale), 0, canvas, 5);

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            measureWidth = (int) getMeasureSize(widthMeasureSpec, true);
            measureHeight = (int) getMeasureSize(heightMeasureSpec, false);
            setMeasuredDimension(measureWidth, measureHeight);
            resultRule = Math.min(measureWidth, measureHeight); //防止布局为长方形，返回较小的变用作参考系
            ratioScale = resultRule / 790f;
        }

        private void drawPoint(int translateX, int translateY, Canvas canvas, int area) {
            canvas.save();
            canvas.translate(translateX, translateY);
            //已选中的点绘制为其他颜色（红色）
            if (list.contains(area)) {
                if (!isRightPath) {
                    pointPaint.setColor(Color.argb(255, 255, 0, 0));
                } else {
                    pointPaint.setColor(Color.argb(255, 75, 217, 191));
                }
                //                pointPaint.setColor(Color.RED);
                pointPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(0, 0, 27 * ratioScale, pointPaint);
            }

            pointPaint.setStyle(Paint.Style.STROKE);
            pointPaint.setStrokeWidth(6 * ratioScale);
            canvas.drawCircle(0, 0, 86 * ratioScale, pointPaint);
            //恢复画笔颜色
            pointPaint.setARGB(255, 166, 166, 166);
            canvas.restore();
        }
    }

    /**
     * 九宫格中的线视图
     */
    private class LineView extends View {

        public LineView(Context context) {
//            super(context);
            this(context, null);
        }

        public LineView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initLinePaint();
        }

        //初始化画线的笔
        private void initLinePaint() {
            linePaint = new Paint();
            //        linePaint.setARGB(255, 101, 188, 80);
            linePaint.setARGB(255, 75, 217, 191);
            linePaint.setStrokeCap(Paint.Cap.ROUND);
            linePaint.setAntiAlias(true);
            linePaint.setDither(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //将画布移到布局空间的中间
            canvas.translate(Math.abs(resultRule - measureWidth) / 2, Math.abs(resultRule - measureHeight) / 2);

            linePaint.setStrokeWidth(8 * ratioScale);    //设置笔触宽度，不能在onDraw之前设置，否则resultRule为初始化值(可以在post中初始化)

            if (!isRightPath) {
                linePaint.setColor(Color.argb(255, 255, 0, 0));
            }

            //从list中获取所需要连接的区域，最后一个区域和手指所在点相连接
            int size = list.size();
            //每一个area都对应一个坐标点
            if (size >= 3) {    //表示至少连接了两个点
                for (int i = 0; i < size - 2; i++) {
                    Float[] from = localPoint[list.get(i)];   //拿到list中第i个区域对应的坐标
                    Float[] to = localPoint[list.get(i + 1)];
                    canvas.drawLine(from[0], from[1], to[0], to[1], linePaint);
                }
            }
            if (size >= 2) {
                if (!isRightPath) {
                    return;
                }
                Float[] listPoint = localPoint[list.get(list.size() - 2)];    //拿到最后一个点连接坐标，从改点画到手指触点的连接
                canvas.drawLine(listPoint[0], listPoint[1], touchX, touchY, linePaint);
            }
            //恢复画笔默认颜色
            linePaint.setColor(Color.argb(255, 75, 217, 191));
        }
    }

    /**
     * 获取录入的手势密码
     *
     * @return 手势密码对应区域的 list
     */
    private ArrayList<Integer> getGesturePath() {
        if (list.size() <= 4) {
            return null;    //必须连接4个点密码才生效
        }
        fingerPath.clear();
        fingerPath.addAll(list);
        fingerPath.remove(fingerPath.size() - 1);     //去除最后一个滑动中的手指按压点
        return fingerPath;
    }

    private float getMeasureSize(int length, boolean isWidth) {
        int size = MeasureSpec.getSize(length); //获取尺寸
        int mode = MeasureSpec.getMode(length); //获取模式
        float resSize = 0;    //最终计算所得大小，被返回
        if (mode == MeasureSpec.EXACTLY) {    //具体指定大小，比如30dp或者match_parent(fill_parent)
            resSize = size;
        } else { //未具体指定大小
            if (mode == MeasureSpec.AT_MOST) {    //wrap_content
                if (isWidth) {      //因为为正方形，该控件中可以不进行判断
                    resSize = 790;
                } else {
                    resSize = 790;
                }
            }
        }
        return resSize;
    }


    /**
     * ################################# 绘制结束的回调接口 #####################################
     */

    private OnDrawLockPathListener onDrawLockPathListener;

    //设置绘制结束的监听
    public void setOnDrawLickPathListener(OnDrawLockPathListener onDrawLickPathListener) {
        this.onDrawLockPathListener = onDrawLickPathListener;
    }

    public interface OnDrawLockPathListener {
        /**
         * 绘制路径结束后的回调函数，传入的参数可能为空，表示连接的点数小于4个
         *
         * @param pathList 路径值List :{ 横向：
         *                 0,1,2,
         *                 3,4,5,
         *                 6,7,8
         *                 }
         * @return 绘制的路径是否是正确路径，正确与否同本地记录的路径匹配，返回true将显示
         * 结果为绿色路径，表示匹配，返回false将显示红色路径，表示路径同本地的不匹配，如果是
         * 新录入路径，直接返回true即可
         */
        boolean onDrawPathFinish(List<Integer> pathList);
    }
}