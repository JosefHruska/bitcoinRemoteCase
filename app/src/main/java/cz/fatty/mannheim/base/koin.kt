package cz.fatty.mannheim.base

import androidx.room.Room
import cz.fatty.mannheim.Db
import cz.fatty.mannheim.api.MainApi
import cz.fatty.mannheim.api.MainApiHandler
import cz.fatty.mannheim.repo.MainRepo
import cz.fatty.mannheim.repo.MainRepoImpl
import cz.fatty.mannheim.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
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
    viewModel { MainViewModel(get()) }
    single { BaseApiService() }
    single {
        MainRepoImpl(
            get(), get()
        ) as MainRepo
    }
    single {
        MainApiHandler(
            getMainApiService(
                getBaseUrl()
            )
        )
    }
    single { get<Db>().bitcoinDao() }
    single {
        Room.databaseBuilder(get(), Db::class.java, "bitcoin-db")
            .build()
    }
}

private fun getMainApiService(baseUrl: String) =
    BaseApiService().getRetro<MainApi>(baseUrl)

private fun getBaseUrl() = BaseApiService.getUrl()