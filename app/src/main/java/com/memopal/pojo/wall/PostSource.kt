package com.memopal.pojo.wall

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostSource (

    @SerializedName("type")
    @Expose
    var type: String? = null

)
