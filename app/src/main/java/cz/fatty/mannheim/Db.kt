package cz.fatty.mannheim

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.fatty.mannheim.objects.BitcoinDailyRate
import cz.fatty.mannheim.objects.BitcoinRate
import cz.fatty.mannheim.repo.BitcoinDao

@Database(
    entities = [BitcoinRate::class, BitcoinDailyRate::class], version = 3, exportSchema = false
)

abstract class Db : RoomDatabase() {

    abstract fun bitcoinDao(): BitcoinDao
}