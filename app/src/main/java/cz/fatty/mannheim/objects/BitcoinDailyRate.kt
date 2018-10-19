package cz.fatty.mannheim.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "DAILY_RATE")
data class BitcoinDailyRate(
    @Json(name = "time") var time: String? = null,
    @Json(name = "average") var average: Float? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}