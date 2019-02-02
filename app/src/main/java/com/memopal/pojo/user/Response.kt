package com.memopal.pojo.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response (

    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null,
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null,
    @SerializedName("is_closed")
    @Expose
    var isClosed: Boolean? = null,
    @SerializedName("can_access_closed")
    @Expose
    var canAccessClosed: Boolean? = null,
    @SerializedName("bdate")
    @Expose
    var bdate: String? = null

)
