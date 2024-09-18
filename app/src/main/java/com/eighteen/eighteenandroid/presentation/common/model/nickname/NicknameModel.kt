package com.eighteen.eighteenandroid.presentation.common.model.nickname

data class NicknameModel(
    val inputString: String = "",
) {
    val status: NicknameStatus
        get() {
            val isValidInput = Regex(REGEX_VALID_INPUT).matches(inputString)
            val isValidLength = Regex(REGEX_LENGTH).matches(inputString)
            return when {
                inputString.isEmpty() -> NicknameStatus.None
                isValidInput && isValidLength -> NicknameStatus.Success
                isValidInput.not() -> NicknameStatus.Error.WrongInput
                else -> NicknameStatus.Error.Length
            }
        }

    companion object {
        private const val REGEX_VALID_INPUT = """[가-힣a-zA-Z0-9]*"""
        private const val REGEX_LENGTH = """^.{2,8}"""
    }
}