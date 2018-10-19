package cz.fatty.mannheim.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import cz.fatty.mannheim.extensions.ld

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    // TODO: Implement progress bar

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
}