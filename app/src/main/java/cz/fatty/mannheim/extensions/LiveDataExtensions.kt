package cz.fatty.mannheim.extensions

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import cz.fatty.mannheim.networking.CallbackWrapper
import cz.fatty.mannheim.repo.ApiData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, doOnObserve: (T) -> (Unit)) {
    var observer: Observer<T>? = null
    observer = Observer { value ->
        if (value != null) {
            doOnObserve(value)
            observer?.let {
                removeObserver(it)
            }
        }
    }
    this.observe(lifecycleOwner, observer)
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, doOnObserve: (T?) -> (Unit)) {
    val observer: Observer<T> = Observer { value ->
        doOnObserve(value)
    }
    this.observe(lifecycleOwner, observer)
}

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, doOnObserve: (T) -> (Unit)) {
    val observer: Observer<T> = Observer { value ->
        if (value != null) {
            doOnObserve(value)
        }
    }
    this.observe(lifecycleOwner, observer)
}

fun <T> LiveData<ApiData<T>>.observeApiData(lifecycleOwner: LifecycleOwner, doOnObserve: (ApiData<T>) -> (Unit)) {
    var observer: Observer<ApiData<T>>? = null
    observer = Observer { value ->
        if (value != null) {
            doOnObserve(value)
            if (!value.isInProgress()) {
                observer?.let {
                    removeObserver(it)
                }
            }
        }
    }
    this.observe(lifecycleOwner, observer)
}

@SuppressLint("CheckResult")
fun <T> Observable<T>.subscribeOnBackground(observer: CallbackWrapper<T>) {
    this.observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribeWith(observer)
}

fun <T> MutableLiveData<T>.withInitialValue(initialValue: T): MutableLiveData<T> {
    this.postValue(initialValue)
    return this
}

fun <T> MediatorLiveData<T>.withInitialValue(initialValue: T): MediatorLiveData<T> {
    this.postValue(initialValue)
    return this
}

fun <A, B> LiveData<A>.map(function: (A) -> B): LiveData<B> = Transformations.map(this, function)

fun <A, B> LiveData<A>.switchMap(function: (A) -> LiveData<B>): LiveData<B> =
    Transformations.switchMap(this, function)