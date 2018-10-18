package cz.fatty.mannheim

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.fatty.mannheim.App.Companion.app
import cz.fatty.mannheim.objects.BitcoinRate
import cz.fatty.mannheim.repo.BitcoinDao

@Database(
    entities = [BitcoinRate::class], version = 1
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
        @Synchronized
        fun getInstance(): Db {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder<Db>(
                    app(), Db::class.java, "ex.db"
                ).fallbackToDestructiveMigration().setJournalMode(RoomDatabase.JournalMode.TRUNCATE).build()
            }
            return sInstance!!
        }
    }
}