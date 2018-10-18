package cz.fatty.dreamer.objects

import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class BitcoinAverage(
    @PrimaryKey @Json( name = "id") val id: Int = -1,
    var time: String? = null,
    var average: Float? = null
)