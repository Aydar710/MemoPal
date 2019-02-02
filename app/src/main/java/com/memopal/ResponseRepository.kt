package com.memopal

import com.memopal.pojo.groups.Group
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ResponseRepository(val vkApi: VkApiService) {

    fun getGroups(queryText: String?, accessToken : String): Single<List<Group>> {
        return vkApi.getGroups(queryText, accessToken)
                .subscribeOn(Schedulers.io())
                .map {
                    it.response?.items
                }
    }
}