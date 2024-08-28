package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias

interface SignUpAddMediasClickListener {
    fun onClickAddMedia()
    fun onClickAddRefMedia()
    fun onClickRemove(position: Int)
    fun onClickRemoveRef()
}