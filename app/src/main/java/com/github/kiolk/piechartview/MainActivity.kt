package com.github.kiolk.piechartview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.kiolk.piechartview.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pirChart: PieChart = findViewById(R.id.pie_chart)
        pirChart.setData(listOf(20, 30, 50, 50, 30, 20, 34, 56,33, 21,9, 22, 65,34,76))
    }
}
