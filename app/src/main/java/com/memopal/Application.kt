package com.memopal

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class Application : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
         lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        //VKSdk.initialize(this)
        VK.addTokenExpiredHandler(tokenTracker)
        context = this
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            // token expired
        }
    }

}