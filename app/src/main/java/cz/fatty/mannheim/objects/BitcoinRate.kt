package cz.fatty.mannheim.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class BitcoinRate(
    @PrimaryKey(autoGenerate = true) @Json(name = "id") var id: Int,
    @Json(name = "time") var time: String? = null,
    @Json(name = "average") var average: Float? = null
)