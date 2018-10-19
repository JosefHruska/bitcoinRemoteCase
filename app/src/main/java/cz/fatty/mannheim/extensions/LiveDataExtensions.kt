package cz.fatty.mannheim.extensions

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cz.fatty.mannheim.networking.CallbackWrapper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, doOnObserve: (T) -> (Unit)) {
    val observer: Observer<T> = Observer { value ->
        if (value != null) {
            doOnObserve(value)
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