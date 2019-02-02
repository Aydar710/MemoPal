package com.memopal.pojo.TypeAndId

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response (

    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("object_id")
    @Expose
    var objectId: Int? = null

)
