package com.memopal.pojo.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserInfoResponse(

        @SerializedName("response")
        @Expose
        var response: List<Response>? = null

)
