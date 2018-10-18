package cz.fatty.mannheim.base

import cz.fatty.mannheim.Db
import cz.fatty.mannheim.api.MainApi
import cz.fatty.mannheim.api.MainApiHandler
import cz.fatty.mannheim.repo.BitcoinDao
import cz.fatty.mannheim.repo.MainRepo
import cz.fatty.mannheim.repo.MainRepoImpl
import cz.fatty.mannheim.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

/**
 * All stuff related to dependency injection
 *
 * @author Pepa Hruska
 */

// Koin module
val myModule: Module = module {
    // If the ViewModel has some parameters - just use get() to get them
    // To succesfully get the parameters you need to add their instance to: single{}
    viewModel { MainViewModel(get("mainRepo")) }
    single { BaseApiService(get("db")) }
    single("mainRepo") {
        MainRepoImpl(
            get("db"), get("mainApi")
        ) as MainRepo
    }
    single("mainApi") {
        MainApiHandler(
            getMainApiService(
                getBaseUrl(), get("db")
            ), get("db")
        )
    }
    single("db") { Db.getInstance() } // This is how to add other Singleton instances
}

/*val testModule: Module = module {

    // In-Memory database config
    single("inMemoryDb") {
        Room.inMemoryDatabaseBuilder(get(), Db::class.java).allowMainThreadQueries().build()
    } bind com.ilab.spaceti.general.room.Db::class
}*/

private fun getMainApiService(baseUrl: String, db: Db) =
    BaseApiService(db).getRetro<MainApi>(baseUrl)

private fun getBaseUrl() = BaseApiService.getUrl()