package cz.fatty.mannheim.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {

    lateinit var viewModel: T

    abstract fun initViewModel()

    abstract fun getLayoutRes(): Int

    abstract fun getContentView(): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        setContentView(getLayoutRes())
        initUi()
    }

    open fun initUi() {}
}
