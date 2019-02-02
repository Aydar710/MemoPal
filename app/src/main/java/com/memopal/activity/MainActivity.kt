package com.memopal.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import android.content.Intent
import android.content.SharedPreferences
import com.facebook.stetho.Stetho
import com.memopal.R
import com.memopal.VkApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val scope = arrayOf(VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL)
   // private var token: String? = null
    private lateinit var sPref: SharedPreferences
    private val request = "https://api.vk.com/method/wall.get?owner_id=-1&count=1&access_token=908b701bf07c2889546abab3234b945f0d9583b8ec06fec8fb4fddf3fc4ae27d4d49084e83ed1c91f9377&v=5.92"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VKSdk.login(this,
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
                VKScope.DIRECT)

        Stetho.initializeWithDefaults(this)

        val apiService = VkApiService.create()


        btn_request.setOnClickListener {
            val token = getString(R.string.access_token)
            apiService.getGroupPosts("-1", "2")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.i("Tag", "${it.response.toString()}")
                    },
                            {
                                it.printStackTrace()
                            })
        }

        btn_request_user.setOnClickListener {
            val token = getString(R.string.access_token)
            apiService.getUser("210700286", "bdate")
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
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        Log.i("Tag", res.accessToken)
                        saveTokenToPreferences(res.accessToken)
                    }

                    override fun onError(error: VKError) {
                        Log.i("Tag", "ERROR")
                    }
                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun saveTokenToPreferences(token: String) {
        sPref = getPreferences(MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sPref.edit()
        editor.putString("token", token)
        editor.commit();
    }

    fun getTokenFromPreferences(): String {
        sPref = getPreferences(MODE_PRIVATE)
        val token = sPref.getString("token", "")
        return token
    }
}
