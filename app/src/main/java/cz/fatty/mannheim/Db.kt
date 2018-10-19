package cz.fatty.mannheim

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.fatty.mannheim.App.Companion.app
import cz.fatty.mannheim.objects.BitcoinRate
import cz.fatty.mannheim.repo.BitcoinDao

@Database(
    entities = [BitcoinRate::class], version = 1, exportSchema = false
)

abstract class Db : RoomDatabase() {

    abstract fun bitcoinDao(): BitcoinDao

    companion object {

        /** The only instance  */
        var sInstance: Db? = null

        /**
         * Gets the singleton instance of SampleDatabase.
         *
         * @param context The context.
         * @return The singleton instance of SampleDatabase.
         */

        fun getInstance(): Db {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(app(), Db::class.java, "user-database")
                    .build()
            }
            return sInstance!!
        }
    }
}