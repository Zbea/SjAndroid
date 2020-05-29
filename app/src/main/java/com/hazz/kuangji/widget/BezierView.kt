package com.hazz.kuangji.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathEffect
import android.graphics.PathMeasure
import android.graphics.Point
import android.util.AttributeSet
import android.view.View


class BezierView : View {
    private var lineSmoothness = 0.2f
    private var mPointList: List<Point>? = null
    private var mPath: Path? = null
    private var mAssistPath: Path? = null
    private var drawScale = 1f
    private var mPathMeasure: PathMeasure? = null
    private val defYAxis = 500f
    private val defXAxis = 0f

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setPointList(pointList: List<Point>) {
        mPointList = pointList
        measurePath()
    }

    fun setLineSmoothness(lineSmoothness: Float) {
        if (lineSmoothness != this.lineSmoothness) {
            this.lineSmoothness = lineSmoothness
            measurePath()
            postInvalidate()
        }
    }

    fun setDrawScale(drawScale: Float) {
        this.drawScale = drawScale
        postInvalidate()
    }

    fun startAnimation(duration: Long) {
        val animator = ObjectAnimator.ofFloat(this, "drawScale", 0f, 1f)
        animator.duration = duration
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        if (mPointList == null)
            return
        //measurePath();
        val paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
        //绘制辅助线
        //  canvas.drawPath(mAssistPath,paint);

        paint.color = Color.YELLOW
        val dst = Path()
        dst.rLineTo(0f, 0f)
        val distance = mPathMeasure!!.length * drawScale
        if (mPathMeasure!!.getSegment(0f, distance, dst, true)) {
            //绘制线
            canvas.drawPath(dst, paint)
            val pos = FloatArray(2)
            mPathMeasure!!.getPosTan(distance, pos, null)
            //绘制阴影
            drawShadowArea(canvas, dst, pos);
            //drawShadowAreaUp(canvas, dst, pos)
            //绘制点
            drawPoint(canvas,pos)
        }
        /*greenPaint.setPathEffect(getPathEffect(mPathMeasure.getLength()));
        canvas.drawPath(mPath, greenPaint);*/
        //mPath.reset();adb shell screenrecord --bit-rate 2000000 /sdcard/test.mp4

    }

    /**
     * 绘制阴影
     * @param canvas
     * @param path
     * @param pos
     */
    private fun drawShadowArea(canvas: Canvas, path: Path, pos: FloatArray) {
        path.lineTo(pos[0], defYAxis)
        path.lineTo(defXAxis, defYAxis)
        path.close()
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#1E2742")
        canvas.drawPath(path, paint)
    }

    private fun drawShadowAreaUp(canvas: Canvas, path: Path, pos: FloatArray) {
        path.lineTo(0f, 0f)
        path.lineTo(mPointList!![0].x.toFloat(), mPointList!![0].y.toFloat())
        path.close()
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.YELLOW
        canvas.drawPath(path, paint)
    }

    /**
     * 绘制点
     * @param canvas
     * @param pos
     */
    private fun drawPoint(canvas: Canvas, pos: FloatArray) {
        val redPaint = Paint()
        redPaint.color = Color.YELLOW
        redPaint.strokeWidth = 3f
        redPaint.style = Paint.Style.FILL
//        for (point in mPointList!!) {
//            if (point.x > pos[0]) {
//                break
//            }
            canvas.drawCircle(mPointList!!.get(mPointList!!.size-1).x.toFloat(),
                    mPointList!!.get(mPointList!!.size-1).y.toFloat(), 10f, redPaint)
//        }
    }

    private fun getPathEffect(length: Float): PathEffect {
        return DashPathEffect(floatArrayOf(length * drawScale, length), 0f)
    }

    private fun measurePath() {
        mPath = Path()
        mAssistPath = Path()
        var prePreviousPointX = java.lang.Float.NaN
        var prePreviousPointY = java.lang.Float.NaN
        var previousPointX = java.lang.Float.NaN
        var previousPointY = java.lang.Float.NaN
        var currentPointX = java.lang.Float.NaN
        var currentPointY = java.lang.Float.NaN
        var nextPointX: Float
        var nextPointY: Float

        val lineSize = mPointList!!.size
        for (valueIndex in 0 until lineSize) {
            if (java.lang.Float.isNaN(currentPointX)) {
                val point = mPointList!![valueIndex]
                currentPointX = point.x.toFloat()
                currentPointY = point.y.toFloat()
            }
            if (java.lang.Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (valueIndex > 0) {
                    val point = mPointList!![valueIndex - 1]
                    previousPointX = point.x.toFloat()
                    previousPointY = point.y.toFloat()
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX
                    previousPointY = currentPointY
                }
            }

            if (java.lang.Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (valueIndex > 1) {
                    val point = mPointList!![valueIndex - 2]
                    prePreviousPointX = point.x.toFloat()
                    prePreviousPointY = point.y.toFloat()
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX
                    prePreviousPointY = previousPointY
                }
            }

            // 判断是不是最后一个点了
            if (valueIndex < lineSize - 1) {
                val point = mPointList!![valueIndex + 1]
                nextPointX = point.x.toFloat()
                nextPointY = point.y.toFloat()
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX
                nextPointY = currentPointY
            }

            if (valueIndex == 0) {
                // 将Path移动到开始点
                mPath!!.moveTo(currentPointX, currentPointY)
                mAssistPath!!.moveTo(currentPointX, currentPointY)
            } else {
                // 求出控制点坐标
                val firstDiffX = currentPointX - prePreviousPointX
                val firstDiffY = currentPointY - prePreviousPointY
                val secondDiffX = nextPointX - previousPointX
                val secondDiffY = nextPointY - previousPointY
                val firstControlPointX = previousPointX + lineSmoothness * firstDiffX
                val firstControlPointY = previousPointY + lineSmoothness * firstDiffY
                val secondControlPointX = currentPointX - lineSmoothness * secondDiffX
                val secondControlPointY = currentPointY - lineSmoothness * secondDiffY
                //画出曲线
                mPath!!.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY)
                //将控制点保存到辅助路径上
                mAssistPath!!.lineTo(firstControlPointX, firstControlPointY)
                mAssistPath!!.lineTo(secondControlPointX, secondControlPointY)
                mAssistPath!!.lineTo(currentPointX, currentPointY)
            }

            // 更新值,
            prePreviousPointX = previousPointX
            prePreviousPointY = previousPointY
            previousPointX = currentPointX
            previousPointY = currentPointY
            currentPointX = nextPointX
            currentPointY = nextPointY
        }
        mPathMeasure = PathMeasure(mPath, false)
    }

}
