package cz.fatty.mannheim.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import cz.fatty.mannheim.extensions.ld
import cz.fatty.mannheim.repo.ApiData

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    val isLoading = MediatorLiveData<Boolean>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        ld("NAVIGATION VIEWMODEL: onCreate called for ${javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        ld("NAVIGATION VIEWMODEL: onStart called for ${javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        ld("NAVIGATION VIEWMODEL: onResume called for ${javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        ld("NAVIGATION VIEWMODEL: onPause called for ${javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        ld("NAVIGATION VIEWMODEL: onStop called for ${javaClass.simpleName}")
    }

    fun setupLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    /**
     * Use this to load data from repository. It pass the requested data back and if there was a error state it saves
     * the error state and post it to list of error states which are used to display errors by InfoBar
     *
     * @param apiData: Data to load
     * @param id: Used to identify purpose of this call
     *
     * @returns result of apiData
     */
    fun <T> load(apiData: LiveData<ApiData<T>>, id: Int): LiveData<ApiData<T>> {
        isLoading.addSource(apiData) {
            if (it != null) {
                isLoading.postValue(it.isInProgress())
            }
        }
        return apiData
    }
}