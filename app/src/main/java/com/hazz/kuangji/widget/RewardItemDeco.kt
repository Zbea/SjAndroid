package com.hazz.kuangji.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RewardItemDeco : RecyclerView.ItemDecoration {
    private var dividerPaint: Paint = Paint()
    private val topOffset: Int
    private val rightOffset: Int
    private val leftOffset: Int
    private val bottomOffset: Int
    private val itemRadius: Int


    constructor(
        topOffset: Int,
        rightOffset: Int,
        leftOffset: Int,
        bottomffset: Int,
        itemRadius: Int
    ) {

        dividerPaint.style = Paint.Style.FILL
        dividerPaint.color = Color.parseColor("#131625")
        this.topOffset = topOffset
        this.rightOffset = rightOffset
        this.leftOffset = leftOffset
        this.bottomOffset = bottomffset
        this.itemRadius = itemRadius
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = topOffset
        outRect.bottom = bottomOffset
        outRect.left = leftOffset
        outRect.right = rightOffset
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
//        val childCount = parent.childCount
//        val left = parent.paddingLeft
//        val right = parent.width - parent.paddingRight
//        for (i in 0..childCount) {
//            val child = parent.getChildAt(i) ?: break
//            val topRect = Rect(left, child.top - topOffset, right, child.top + itemRadius)
//            c.drawRect(topRect, dividerPaint)
//
//            val bottomRect = Rect(left, child.bottom - itemRadius, right, child.bottom + bottomOffset)
//            c.drawRect(bottomRect, dividerPaint)
//
//            val leftRect = Rect(left, child.top, child.left + itemRadius, child.bottom)
//            c.drawRect(leftRect, dividerPaint)
//
//            val rightRect = Rect(child.right - itemRadius, child.top, right, child.bottom)
//            c.drawRect(rightRect, dividerPaint)
//
//        }

    }


}