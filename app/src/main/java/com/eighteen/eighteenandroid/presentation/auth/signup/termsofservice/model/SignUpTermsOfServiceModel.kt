package com.eighteen.eighteenandroid.presentation.auth.signup.termsofservice.model

data class SignUpTermsOfServiceModel(
    val isCheckedTermsOfService: Boolean = false,
    val isCheckedPrivacyPolicy: Boolean = false,
    val isCheckedNotification: Boolean = false
) {
    val isCheckedAll get() = isCheckedNotification && isCheckedPrivacyPolicy && isCheckedTermsOfService
    val isCheckedRequiredAll get() = isCheckedPrivacyPolicy && isCheckedTermsOfService
}
