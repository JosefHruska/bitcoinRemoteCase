package cz.fatty.mannheim

import android.app.Application
import com.crashlytics.android.Crashlytics
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
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        val crashlytics = Crashlytics.Builder().build()
    }

    companion object {

        private var gApp: App? = null

        fun app() = checkNotNull(gApp, { "App not initialized" })
    }
}