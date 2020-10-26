package com.leu8.charts

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat


class RingProgressChartView(context: Context?): View(context) {
    private val path: Path = Path()
    private val paint: Paint = Paint()
    private val oval: RectF = RectF()

    private var percentage: Int = 0
    private var barColor: Int = 0
    private var barBackgroundColor: Int = 0

    constructor(context: Context, attrs: AttributeSet): this(context) {
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RingProgressChartView)

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

        percentage = attributes.getInteger(R.styleable.RingProgressChartView_ringPercentage, 0)
        if (percentage > 100)
            percentage = 100

        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()

        val radius: Float
        radius = if (width > height) {
            height / 2.2f
        } else {
            width / 2.2f
        }

        path.addCircle(
            width / 2,
            height / 2, radius,
            Path.Direction.CW
        )

        paint.color = barBackgroundColor
        paint.strokeWidth = 35f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND

        val centerX: Float
        val centerY: Float
        centerX = width / 2
        centerY = height / 2

        oval[centerX - radius, centerY - radius, centerX + radius] = centerY + radius

        canvas.drawArc(oval, 0f, 360f, false, paint)

        paint.color = barColor

        val angle = (this.percentage * 360 / 100).toFloat()
        canvas.drawArc(oval, 270f, -angle, false, paint)
    }
}