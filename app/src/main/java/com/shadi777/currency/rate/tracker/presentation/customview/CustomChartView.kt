package com.shadi777.currency.rate.tracker.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.shadi777.currency.rate.tracker.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CustomChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var data: List<Pair<Long, Double>> = emptyList()
    private var timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val diffSampling: Float = 0.15f

    private val linePaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.color_orange)
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val gridPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.label_tertiary)
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    private val textPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.label_primary)
        textSize = 40f
        isAntiAlias = true
    }


    fun setData(data: List<Pair<Long, Double>>, range: String) {
        this.data = data
        this.timeFormat = getTimeFormatForRange(range)
        invalidate()
    }

    private fun getTimeFormatForRange(range: String): SimpleDateFormat {
        return when (range) {
            "day" -> SimpleDateFormat("HH:mm", Locale.getDefault())
            "week" -> SimpleDateFormat("dd MMM", Locale.getDefault())
            "month" -> SimpleDateFormat("dd MMM", Locale.getDefault())
            "half_year" -> SimpleDateFormat("MMM yy", Locale.getDefault())
            "year" -> SimpleDateFormat("MMM yy", Locale.getDefault())
            else -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (data.isEmpty()) return

        val paddingLeft = 100f
        val paddingBottom = 100f
        val paddingTop = 50f
        val paddingRight = 50f

        val widthGraph = width - paddingLeft - paddingRight
        val heightGraph = height - paddingTop - paddingBottom

        val minn = data.minOf { it.second }
        val maxx = data.maxOf { it.second }
        val diff = maxx - minn
        val minPrice = minn - (diffSampling * diff)
        val maxPrice =  maxx + (diffSampling * diff)
        val minTime = data.minOf { it.first }
        val maxTime = data.maxOf { it.first }

        val path = Path()
        data.forEachIndexed { index, point ->
            val x = paddingLeft + ((point.first - minTime) / (maxTime - minTime).toFloat()) * widthGraph
            val y = paddingTop + (1 - (point.second - minPrice).toFloat() / (maxPrice - minPrice).toFloat()) * heightGraph

            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        canvas.drawPath(path, linePaint)

        // Call<String>()
        // OkHttpClient
        // TypeAdapter
        //
        drawGridAndLabels(canvas, paddingLeft, paddingTop, widthGraph, heightGraph, minPrice, maxPrice, minTime, maxTime)
    }

    private fun drawGridAndLabels(
        canvas: Canvas,
        paddingLeft: Float,
        paddingTop: Float,
        widthGraph: Float,
        heightGraph: Float,
        minPrice: Double,
        maxPrice: Double,
        minTime: Long,
        maxTime: Long
    ) {
        val stepsY = 5
        val stepPrice = (maxPrice - minPrice) / stepsY

        for (i in 0..stepsY) {
            val price = minPrice + i * stepPrice
            val y = paddingTop + (1 - (price - minPrice).toFloat() / (maxPrice - minPrice).toFloat()) * heightGraph

            canvas.drawLine(paddingLeft, y, paddingLeft + widthGraph, y, gridPaint)
            canvas.drawText("%.2f".format(price), 10f, y, textPaint)
        }

        val stepsX = 4
        val stepTime = (maxTime - minTime) / stepsX

        for (i in 0..stepsX) {
            val time = minTime + i * stepTime
            val x = paddingLeft + ((time - minTime) / (maxTime - minTime).toFloat()) * widthGraph

            canvas.drawLine(x, paddingTop, x, paddingTop + heightGraph, gridPaint)
            canvas.drawText(timeFormat.format(Date(time * 1000)), x, height - 20f, textPaint)
        }
    }
}
