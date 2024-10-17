package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.response.SnsPlatformResponse
import com.eighteen.eighteenandroid.domain.model.SnsInfo

object SnsPlatformMapper {
    fun SnsPlatformResponse.toSnsInfoList() = listOfNotNull(
        instagram?.let { SnsInfo(SnsInfo.SnsType.INSTAGRAM, it) },
        x?.let { SnsInfo(SnsInfo.SnsType.X, it) },
        tiktok?.let { SnsInfo(SnsInfo.SnsType.TIKTOK, it) },
        youtube?.let { SnsInfo(SnsInfo.SnsType.YOUTUBE, it) },
    )
}