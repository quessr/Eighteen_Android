package com.eighteen.eighteenandroid.presentation.auth.signup

import java.util.Date

/**
 * 회원가입 과정 중 받는 정보들을 저장하기 위한 interface
 */
interface SignUpViewModelContentInterface {
    var id: String
    var nickName: String
    var birth: Date
    var school: String
    var medias: List<String>
}