package com.memopal.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.content.SharedPreferences
import com.facebook.stetho.Stetho
import com.memopal.R
import com.memopal.VkApiService
import com.memopal.constants.SHARED_PREF_FILENAME
import com.memopal.constants.SHARED_PREF_KEY
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val scope = arrayOf(VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL)
    // private var token: String? = null
    private lateinit var sPref: SharedPreferences
    private val request = "https://api.VK.com/method/wall.get?owner_id=-1&count=1&access_token=908b701bf07c2889546abab3234b945f0d9583b8ec06fec8fb4fddf3fc4ae27d4d49084e83ed1c91f9377&v=5.92"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Старая версия vk api
        /*VK.login(this,
                VKScope.NOTIFY,
                VKScope.FRIENDS,
                VKScope.PHOTOS,
                VKScope.AUDIO,
                VKScope.VIDEO,
                VKScope.DOCS,
                VKScope.NOTES,
                VKScope.PAGES,
                VKScope.STATUS,
                VKScope.WALL,
                VKScope.GROUPS,
                VKScope.MESSAGES,
                VKScope.NOTIFICATIONS,
                VKScope.STATS,
                VKScope.ADS,
                VKScope.OFFLINE,
                VKScope.EMAIL,
                VKScope.NOHTTPS,
                VKScope.DIRECT)*/
        VK.login(this, arrayListOf(VKScope.WALL, VKScope.GROUPS))

        Stetho.initializeWithDefaults(this)

        val apiService = VkApiService.create()


        btn_request.setOnClickListener {
           // val token = getString(R.string.access_token)
            val token = getTokenFromPreferences()
            apiService.getGroupPosts("-1", "2", token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.i("Tag", it.response.toString())
                    },
                            {
                                it.printStackTrace()
                            })
        }

        btn_request_user.setOnClickListener {
            //val token = getString(R.string.access_token)
            val token = getTokenFromPreferences()
            apiService.getUser("210700286", "bdate", token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.i("Tag", "${it.response?.get(0)?.firstName}")
                    },
                            {
                                it.printStackTrace()
                            })
        }


        btn_groups.setOnClickListener {
            val intent = Intent(this, GroupsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                saveTokenToPreferences(token.accessToken)
                print("a")
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun saveTokenToPreferences(token: String) {
        sPref = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()
        editor.putString("token", token)
        editor.apply();
    }

    fun getTokenFromPreferences(): String {
        sPref = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE)
        val token = sPref.getString(SHARED_PREF_KEY, "")
        return token
    }
}
