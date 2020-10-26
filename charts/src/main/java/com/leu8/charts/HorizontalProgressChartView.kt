package com.leu8.charts

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat


class HorizontalProgressChartView(context: Context?): View(context) {
    private val paint: Paint = Paint()

    private var percentage: Int = 0
    private var barColor: Int = 0
    private var barBackgroundColor: Int = 0

    constructor(context: Context, attrs: AttributeSet): this(context) {
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressChartView)

        val barColorId = attributes.getResourceId(R.styleable.HorizontalProgressChartView_horizontalBarColor, 0)
        barColor = if (barColorId == 0) {
            val barColorString = attributes.getString(R.styleable.HorizontalProgressChartView_horizontalBarColor)
            if (barColorString.isNullOrEmpty()) {
                ContextCompat.getColor(context, R.color.colorPrimary)
            } else {
                Color.parseColor(barColorString)
            }
        } else {
            ContextCompat.getColor(context, barColorId)
        }

        val barBackgroundColorId = attributes.getResourceId(R.styleable.HorizontalProgressChartView_horizontalBarBackgroundColor, 0)
        barBackgroundColor = if (barBackgroundColorId == 0) {
            val barBackgroundColorString = attributes.getString(R.styleable.HorizontalProgressChartView_horizontalBarBackgroundColor)
            if (barBackgroundColorString.isNullOrEmpty()) {
                ContextCompat.getColor(context, R.color.colorLightGray)
            } else {
                Color.parseColor(barBackgroundColorString)
            }
        } else {
            ContextCompat.getColor(context, barBackgroundColorId)
        }

        percentage = attributes.getInteger(R.styleable.HorizontalProgressChartView_horizontalPercentage, 0)
        if (percentage > 100)
            percentage = 100

        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()

        paint.strokeWidth = 35f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND

        val rectStart = width * 0.03f
        val rectEnd = width * 0.97f

        val centerY: Float
        centerY = height / 2

        paint.color = barBackgroundColor
        canvas.drawLine(rectStart, centerY, rectEnd, centerY, paint)

        val rectLength = rectEnd - rectStart
        val progress = (this.percentage * rectLength / 100)

        paint.color =  barColor
        if (percentage > 1)
            canvas.drawLine(rectStart, centerY, progress + rectStart, centerY, paint)
    }
}
