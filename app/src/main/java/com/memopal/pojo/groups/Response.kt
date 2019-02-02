package com.memopal.pojo.groups

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response (

    @SerializedName("count")
    @Expose
    var count: Int? = null,
    @SerializedName("items")
    @Expose
    var items: List<Group>? = null

)
