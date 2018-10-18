package cz.fatty.mannheim.repo

import androidx.room.Dao
import androidx.room.Insert
import cz.fatty.mannheim.objects.BitcoinRate

@Dao
interface BitcoinDao {

    @Insert
    fun saveRates(rates: BitcoinRate)

}