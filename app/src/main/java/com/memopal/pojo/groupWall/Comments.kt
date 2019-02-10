package com.memopal.pojo.groupWall

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 data class Comments (

    @SerializedName("count")
    @Expose
    var count: Int? = null,
    @SerializedName("can_post")
    @Expose
    var canPost: Int? = null

)
