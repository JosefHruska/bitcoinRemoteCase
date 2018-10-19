package cz.fatty.mannheim.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import cz.fatty.mannheim.extensions.lw
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong

class ChartDateFormatter : IAxisValueFormatter {

    val sdf = SimpleDateFormat("hh:mm", Locale.ENGLISH)

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return sdf.format(Date(value.roundToLong()))
    }
}