package com.eighteen.eighteenandroid.domain.model

class Participant(
    val name: String,
    val school: String,
    val age: Int,
    val imgUrl: String,
    val hasVideo: Boolean = false,
    val winningRound: Int? = null
)