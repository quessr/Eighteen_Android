package com.eighteen.eighteenandroid.domain.model

sealed class Tournament {
    object Exercise: Tournament()
    object Study: Tournament()
}