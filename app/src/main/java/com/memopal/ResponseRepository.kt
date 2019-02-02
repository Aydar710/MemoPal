package com.memopal

import com.memopal.pojo.groups.Group
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ResponseRepository(val vkApi: VkApiService) {

    fun getGroups(queryText: String?): Single<List<Group>> {
        return vkApi.getGroups(queryText)
                .subscribeOn(Schedulers.io())
                .map {
                    it.response?.items
                }
    }
}