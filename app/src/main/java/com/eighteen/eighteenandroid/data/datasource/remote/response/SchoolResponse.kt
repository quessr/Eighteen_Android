package com.eighteen.eighteenandroid.data.datasource.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SchoolResponse(
    @Json(name = "SCHUL_NM")
    val schoolName: String,
    @Json(name = "ORG_RDNMA")
    val address: String
)
