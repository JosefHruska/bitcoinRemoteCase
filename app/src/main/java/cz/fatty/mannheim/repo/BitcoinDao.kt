package cz.fatty.mannheim.repo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.fatty.mannheim.objects.BitcoinDailyRate
import cz.fatty.mannheim.objects.BitcoinRate

@Dao
interface BitcoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveHourlyRates(rates: List<BitcoinRate>)

    @Query("SELECT * FROM RATE ")
    fun getHourlyRates(): LiveData<List<BitcoinRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDailyRates(rates: List<BitcoinDailyRate>)

    @Query("DELETE FROM DAILY_RATE")
    fun nukeDailyRates()

    @Query("DELETE FROM RATE")
    fun nukeRates()

    @Query("SELECT * FROM DAILY_RATE ")
    fun getDailyRates(): LiveData<List<BitcoinDailyRate>>

}