package com.memopal.activitiesAndFragments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import com.facebook.stetho.Stetho
import com.memopal.R
import com.memopal.constants.SHARED_PREF_FILENAME
import com.memopal.constants.SHARED_PREF_TOKEN_KEY
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){


    private val scope = arrayOf(VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL)
    // private var token: String? = null
    private lateinit var sPref: SharedPreferences
    private val request = "https://api.VK.com/method/wall.get?owner_id=-1&count=1&access_token=908b701bf07c2889546abab3234b945f0d9583b8ec06fec8fb4fddf3fc4ae27d4d49084e83ed1c91f9377&v=5.92"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)

        VK.login(this, arrayListOf(VKScope.WALL, VKScope.GROUPS))


        /*btn_request.setOnClickListener {
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


        */
        btn_groups.setOnClickListener {
            val intent = Intent(this, GroupsActivity::class.java)
            startActivity(intent)
        }

        btn_begin.setOnClickListener {
            startContainerActivity()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                saveTokenToPreferences(token.accessToken)
                // startGroupsActivity()

            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



    private fun startGroupsActivity() {
        val intent = Intent(this, GroupsActivity::class.java)
        startActivity(intent)
    }

    private fun startContainerActivity() {
        val intent = Intent(this, FragmentContainerActivity::class.java)
        startActivity(intent)
    }

    fun saveTokenToPreferences(token: String) {
        sPref = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()
        editor.putString(SHARED_PREF_TOKEN_KEY, token)
        editor.apply();
    }

    fun getTokenFromPreferences(): String {
        sPref = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE)
        val token = sPref.getString(SHARED_PREF_TOKEN_KEY, "")
        return token
    }

    fun doGroupsTransaction() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, UserGroupsFragment.newInstance(116812347))
                .commit()
    }

}
