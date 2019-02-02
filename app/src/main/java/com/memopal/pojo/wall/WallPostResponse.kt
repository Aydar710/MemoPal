package com.memopal.pojo.wall

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WallPostResponse(

        @SerializedName("response")
        @Expose
        var response: Response? = null

)
