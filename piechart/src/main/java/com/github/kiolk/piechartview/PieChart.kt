package com.github.kiolk.piechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import kotlin.math.min

class PieChart @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attr, defStyleAttr) {

    init {
        setup(context)
    }

    private lateinit var path: Path
    private lateinit var paint: Paint
    private var size: Int = 0
    private var radius: Int = 0
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
            preparedData.forEach{ sector ->
                drawSector(this, sector)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawSector(canvas: Canvas?, sector: Sector) {
        canvas?.apply {
            paint.color = sector.color
            drawArc(0F, 0F, size.toFloat(), size.toFloat(), sector.startAngle, sector.sweepAngle, true, paint)
            paint.color = circleBackGround
            drawArc(pieWidth, pieWidth, size - pieWidth, size - pieWidth, sector.startAngle, sector.sweepAngle, true, paint)
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

        //TODO check correct present Hex for int variables
        private val BEEKEEPER : Int = 0xFFF6E58D.toInt()
        private val SPICED_NECTARINE : Int = 0xFFffbe76.toInt()
        private val JUNE_BUD : Int = 0XFFBADC58.toInt()
        private val PINK_GLAMOUR : Int = 0XFFFF7979.toInt()
        private val COSTAL_BREEZE : Int = 0XFFDFF9FB.toInt()
        private val TURBO : Int = 0XFFF9CA24.toInt()
        private val QUINCE_JELLY : Int = 0XFFF0932B.toInt()
        private val CARMINE_PINK : Int = 0XFFEB4D4B.toInt()
        private val PURE_APPLE : Int = 0XFF6AB04C.toInt()
        private val HINT_OF_ICE_PACK : Int = 0XFFC7ECEE.toInt()
        private val MIDDLE_BLUE : Int = 0XFF7ED6DF.toInt()
        private val HELIOTROPE : Int = 0XFFE056FD.toInt()
        private val EXODUCE_FRUIT : Int = 0XFF686DE0.toInt()
        private val DEEP_COAMARU : Int = 0XFF30336B.toInt()
        private val SOARING_IGGLE : Int = 0XFF95AFC0.toInt()
        private val GREENLAND_GREEN : Int = 0XFF22A6B3.toInt()
        private val STEEL_PINK : Int = 0XFFBE2EDD.toInt()
        private val BLURPLE : Int = 0XFFBE2EDD.toInt()
        private val DEEP_COVE : Int = 0XFF130F40.toInt()
        private val WIZARD_GRAY : Int = 0XFF535C68.toInt()

        private val DEFAULT_COLOR_LIST = listOf(
            BEEKEEPER, SPICED_NECTARINE, JUNE_BUD, PINK_GLAMOUR, COSTAL_BREEZE, TURBO, QUINCE_JELLY,
            CARMINE_PINK, PURE_APPLE, HINT_OF_ICE_PACK, MIDDLE_BLUE, HELIOTROPE, EXODUCE_FRUIT, DEEP_COAMARU, SOARING_IGGLE,
            GREENLAND_GREEN, STEEL_PINK, BLURPLE, DEEP_COVE, WIZARD_GRAY
        )
    }
}