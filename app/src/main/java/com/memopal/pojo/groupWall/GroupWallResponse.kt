package com.memopal.pojo.groupWall

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GroupWallResponse (

    @SerializedName("response")
    @Expose
    var response: Response? = null

)
