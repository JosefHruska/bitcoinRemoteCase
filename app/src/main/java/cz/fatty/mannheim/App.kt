package cz.fatty.mannheim

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import cz.fatty.mannheim.base.myModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        gApp = this
        // Start Koin
        startKoin(this, listOf(myModule))

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    companion object {

        private var gApp: App? = null

        fun app() = checkNotNull(gApp, { "App not initialized" })
    }
}