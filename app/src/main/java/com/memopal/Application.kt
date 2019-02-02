package com.memopal

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.vk.sdk.VKSdk

class Application : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
         lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(this)
        context = this
    }

}