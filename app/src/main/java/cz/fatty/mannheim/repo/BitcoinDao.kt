package cz.fatty.mannheim.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.mikephil.charting.data.Entry
import cz.fatty.mannheim.objects.BitcoinRate

@Dao
interface BitcoinDao {

    @Insert
    fun saveRates(rates: List<BitcoinRate>)

    @Query("SELECT * FROM BitcoinRate ")
    fun getRates() : LiveData<List<BitcoinRate>>

}