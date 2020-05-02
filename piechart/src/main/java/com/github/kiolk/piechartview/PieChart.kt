package com.github.kiolk.piechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.min

class PieChart @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attr, defStyleAttr) {

    init {
        setup(context)
    }

    private lateinit var path: Path
    private lateinit var paint: Paint
    private var size: Int = 0
    private var radius: Int = 0
    private var heightDiff = 0
    private var widthDiff = 0
    private var pieWidth: Float = DEFAULT_PIE_WIDTH
    @ColorInt
    private var circleBackGround: Int = DEFAULT_CIRCLE_BACKGROUND
    private var rawData: List<Float> = emptyList()
    private var preparedData: List<Sector> = emptyList()
    private var colorList: List<Int> = DEFAULT_COLOR_LIST

    private fun setup(context: Context) {
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

        if(selectedHeight > selectedWidth) {
            heightDiff = (selectedHeight - selectedWidth) / 2
        }else {
            widthDiff = (selectedWidth - selectedHeight) / 2
        }

        val sideSize = min(selectedHeight, selectedWidth)
        size = sideSize
        radius = size / 2
        setMeasuredDimension(sideSize, sideSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            paint.style = Paint.Style.FILL
            preparedData.forEach{ sector ->
                drawSector(this, sector)
            }
            drawCentralArea(this)
        }
    }

    private fun drawSector(canvas: Canvas?, sector: Sector) {
        canvas?.apply {
            paint.color = sector.color
            drawArc(0F + widthDiff, 0F + heightDiff , size.toFloat() + widthDiff, size.toFloat() + heightDiff, sector.startAngle, sector.sweepAngle, true, paint)
        }
    }

    private fun drawCentralArea(canvas: Canvas?) {
        canvas?.apply {
            paint.color = circleBackGround
            drawArc(pieWidth + widthDiff, pieWidth + heightDiff, size - pieWidth + widthDiff, size - pieWidth + heightDiff, 0f, 360f, true, paint)
        }
    }

    fun <T> setData(data: List<T>) where T: Number{
        this.rawData = data.map { it.toFloat() }
        prepareData()
    }

    private fun prepareData(){
        val finalValue = rawData.sum()
        var previewAngle = 0f
        val sectors: MutableList<Sector> = mutableListOf()
        rawData.forEachIndexed { index, value ->
            val percent = value / finalValue
            val angle = 360 * percent
            val nexAngle = previewAngle + angle
            val sector = Sector(previewAngle, angle, colorList[index])
            previewAngle = nexAngle
            sectors.add(sector)
        }

        preparedData = sectors
    }

    companion object {
        private const val DEFAULT_PIE_WIDTH = 60f
        private const val DEFAULT_CIRCLE_BACKGROUND = Color.WHITE
        private const val BEEKEEPER : Int = 0xFFF6E58.toInt()
        private const val SPICED_NECTARINE = 0xFFffbe76.toInt()
        private const val JUNE_BUD : Int = 0XFFBADC58.toInt()
        private const val PINK_GLAMOUR : Int = 0XFFFF7979.toInt()
        private const val COSTAL_BREEZE : Int = 0XFFDFF9FB.toInt()
        private const val TURBO : Int = 0XFFF9CA24.toInt()
        private const val QUINCE_JELLY : Int = 0XFFF0932B.toInt()
        private const val CARMINE_PINK : Int = 0XFFEB4D4B.toInt()
        private const val PURE_APPLE : Int = 0XFF6AB04C.toInt()
        private const val HINT_OF_ICE_PACK : Int = 0XFFC7ECEE.toInt()
        private const val MIDDLE_BLUE : Int = 0XFF7ED6DF.toInt()
        private const val HELIOTROPE : Int = 0XFFE056FD.toInt()
        private const val EXODUCE_FRUIT : Int = 0XFF686DE0.toInt()
        private const val DEEP_COAMARU : Int = 0XFF30336B.toInt()
        private const val SOARING_IGGLE : Int = 0XFF95AFC0.toInt()
        private const val GREENLAND_GREEN : Int = 0XFF22A6B3.toInt()
        private const val STEEL_PINK : Int = 0XFFBE2EDD.toInt()
        private const val BLURPLE : Int = 0XFFBE2EDD.toInt()
        private const val DEEP_COVE : Int = 0XFF130F40.toInt()
        private const val WIZARD_GRAY : Int = 0XFF535C68.toInt()

        private val DEFAULT_COLOR_LIST = listOf(
            BEEKEEPER, SPICED_NECTARINE, JUNE_BUD, PINK_GLAMOUR, COSTAL_BREEZE, TURBO, QUINCE_JELLY,
            CARMINE_PINK, PURE_APPLE, HINT_OF_ICE_PACK, MIDDLE_BLUE, HELIOTROPE, EXODUCE_FRUIT, DEEP_COAMARU, SOARING_IGGLE,
            GREENLAND_GREEN, STEEL_PINK, BLURPLE, DEEP_COVE, WIZARD_GRAY
        )
    }
}