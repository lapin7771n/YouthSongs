package com.nlapin.youthsongs

import android.app.Application

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.firebase.FirebaseApp
import com.nlapin.youthsongs.di.ApplicationModule
import com.nlapin.youthsongs.di.DaggerMainComponent
import com.nlapin.youthsongs.di.MainComponent

import io.fabric.sdk.android.Fabric

class YouthSongsApp : Application() {

    var component: MainComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Fabric.with(this, Crashlytics())
        component = buildComponent()

        Answers.getInstance().logContentView(ContentViewEvent().putContentName(getString(R.string.app_opened)))
    }

    protected fun buildComponent(): MainComponent {
        return DaggerMainComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
