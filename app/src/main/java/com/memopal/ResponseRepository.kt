package com.memopal

import com.memopal.pojo.groups.Group
import com.memopal.pojo.groupWall.Item
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResponseRepository(val vkApi: VkApiService) {

    fun getGroups(queryText: String?, accessToken: String): Single<List<Group>> {
        return vkApi.getGroups(queryText, accessToken)
                .subscribeOn(Schedulers.io())
                .map {
                    it.response?.items
                }
    }

    fun getUsersGroups(userId: String, count: String = "30", token: String): Single<List<Group>?> {
        return vkApi.getUsersGroups(userId, count, token = token)
                .subscribeOn(Schedulers.io())
                .map { it.response?.items }

    }

    fun getGroupPosts(ownerId: String, accessToken: String, count: String = "20"): Single<List<Item>?> {
        return vkApi.getGroupPosts(ownerId, count, accessToken)
                .map {
                    it.response
                }
                .map {
                    it.items
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}