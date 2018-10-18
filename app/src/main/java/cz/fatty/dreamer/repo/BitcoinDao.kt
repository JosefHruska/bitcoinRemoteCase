package cz.fatty.dreamer.repo

import androidx.room.Dao
import androidx.room.Insert
import cz.fatty.dreamer.objects.BitcoinAverage

@Dao
interface BitcoinDao {

    @Insert
    fun saveRates(rates: BitcoinAverage)

}