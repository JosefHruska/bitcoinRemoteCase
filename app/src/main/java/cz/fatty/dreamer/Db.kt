package cz.fatty.dreamer

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.fatty.dreamer.objects.BitcoinAverage
import cz.fatty.dreamer.repo.BitcoinDao

@Database(
    entities = [BitcoinAverage::class], version = 1
)

abstract class Db : RoomDatabase() {

    abstract fun baseDao(): BitcoinDao

}