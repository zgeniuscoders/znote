package cd.zgeniuscoders.znote

import android.app.Application
import cd.zgeniuscoders.znote.note.di.noteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ZnoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                noteModule
            )
        }
    }

}