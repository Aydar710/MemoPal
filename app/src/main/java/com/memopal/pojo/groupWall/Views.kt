package com.memopal.pojo.groupWall

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Views(

        @SerializedName("count")
        @Expose
        var count: Int? = null

)