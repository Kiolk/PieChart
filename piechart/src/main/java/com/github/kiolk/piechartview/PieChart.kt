package com.github.kiolk.piechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import kotlin.math.min

class PieChart : View {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        init(context)
    }

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        init(context)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attr,
        defStyleAttr,
        defStyleRes
    ) {
        init(context)
    }

    private lateinit var path: Path
    private lateinit var paint: Paint
    private var size: Int = 0
    private var radius: Int = 0

    private fun init(context: Context) {
        path = Path()
        paint = Paint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultWidth = 500
        val defaultHeight = 500

        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val selectedWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> width
            MeasureSpec.AT_MOST -> min(defaultWidth, width)
            MeasureSpec.UNSPECIFIED -> defaultWidth
            else -> defaultWidth
        }

        val selectedHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> height
            MeasureSpec.AT_MOST -> min(defaultHeight, height)
            MeasureSpec.UNSPECIFIED -> defaultHeight
            else -> defaultHeight
        }

        val sideSize = min(selectedHeight, selectedWidth)
        size = sideSize
        radius = size / 2
        setMeasuredDimension(sideSize, sideSize)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            paint.style = Paint.Style.FILL
            paint.color = Color.RED
            paint.strokeWidth = 3f
            drawArc(0F, 0F, size.toFloat(), size.toFloat(), 0F, 45F, true, paint)
            paint.color = Color.WHITE
            drawArc(20f, 20f, size - 20f.toFloat(), size - 20f.toFloat(), 0F, 45F, true, paint)
        }
    }
}