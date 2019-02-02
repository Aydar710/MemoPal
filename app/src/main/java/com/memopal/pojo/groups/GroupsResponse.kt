package com.memopal.pojo.groups

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GroupsResponse (

    @SerializedName("response")
    @Expose
    var response: Response? = null

)
