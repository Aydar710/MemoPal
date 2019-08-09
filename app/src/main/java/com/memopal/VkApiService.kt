package com.memopal

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.memopal.pojo.groupWall.GroupWallResponse
import com.memopal.pojo.TypeAndId.TypeAndIdResponse
import com.memopal.pojo.groups.GroupsResponse
import com.memopal.pojo.user.UserInfoResponse
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApiService {

    @GET("wall.get")
    fun getGroupPosts(
            @Query("owner_id") ownerId: String,
            @Query("count") count: String,
            @Query("access_token") access_token : String
    ): Single<GroupWallResponse>


    @GET("users.get")
    fun getUser(
            @Query("user_ids") userId: String,
            @Query("fields") fields: String,
            @Query("access_token") access_token : String
    ): Observable<UserInfoResponse>

    @GET("utils.resolveScreenName")
    fun getTypeAndIdByScreenName(
            @Query("screen_name") screenName: String
    ): Single<TypeAndIdResponse>

    @GET("groups.search")
    fun getGroups(
            @Query("q") queryText : String?,
            @Query("access_token") access_token : String
//            @Query("type") type : String = "group"
    ) : Single<GroupsResponse>

    @GET("groups.get")
    fun getUsersGroups(
            @Query("user_id") userId : String,
            @Query("extended") extended : String = "1",
            @Query("count") count : String = "30",
            @Query("access_token") token : String
    ) : Single<GroupsResponse>


    companion object Factory {
        fun create(): VkApiService {

            val okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())

            okHttpClient.interceptors().add(Interceptor { chain ->
                var request = chain.request()
                val context = Application.context
                val url = request.url().newBuilder()
                        .addQueryParameter("v", "5.92")
                        //.addQueryParameter("access_token", context.getString(R.string.access_token))
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            })

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.vk.com/method/")
                    .client(okHttpClient.build())
                    .build()

            return retrofit.create(VkApiService::class.java)
        }

    }
}