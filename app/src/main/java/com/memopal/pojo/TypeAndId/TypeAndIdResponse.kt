package com.memopal.pojo.TypeAndId

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TypeAndIdResponse (

    @SerializedName("response")
    @Expose
    var response: Response? = null

)
