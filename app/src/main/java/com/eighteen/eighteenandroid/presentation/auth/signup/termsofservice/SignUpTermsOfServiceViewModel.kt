package com.eighteen.eighteenandroid.presentation.auth.signup.termsofservice

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.auth.signup.termsofservice.model.SignUpTermsOfServiceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpTermsOfServiceViewModel : ViewModel() {
    private val _termsOfServiceModelStateFlow = MutableStateFlow(SignUpTermsOfServiceModel())
    val termsOfServiceModelStateFlow: StateFlow<SignUpTermsOfServiceModel> =
        _termsOfServiceModelStateFlow

    fun checkAll() {
        val isChecked = termsOfServiceModelStateFlow.value.isCheckedAll.not()
        _termsOfServiceModelStateFlow.value = SignUpTermsOfServiceModel(
            isCheckedTermsOfService = isChecked,
            isCheckedPrivacyPolicy = isChecked,
            isCheckedNotification = isChecked
        )
    }

    fun checkTermsOfService() {
        _termsOfServiceModelStateFlow.value =
            termsOfServiceModelStateFlow.value.copy(isCheckedTermsOfService = termsOfServiceModelStateFlow.value.isCheckedTermsOfService.not())
    }

    fun checkPrivacyPolicy() {
        _termsOfServiceModelStateFlow.value =
            termsOfServiceModelStateFlow.value.copy(isCheckedPrivacyPolicy = termsOfServiceModelStateFlow.value.isCheckedPrivacyPolicy.not())
    }

    fun checkNotification() {
        _termsOfServiceModelStateFlow.value =
            termsOfServiceModelStateFlow.value.copy(isCheckedNotification = termsOfServiceModelStateFlow.value.isCheckedNotification.not())
    }

    fun clear(){
        _termsOfServiceModelStateFlow.value = SignUpTermsOfServiceModel()
    }
}