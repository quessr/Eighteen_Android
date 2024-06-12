package com.eighteen.eighteenandroid.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    init {
        fetchUserData()
    }

    private val _userData = MutableLiveData<List<User>>()
    val userData: LiveData<List<User>> = _userData

    private fun fetchUserData() {
        viewModelScope.launch {
            userUseCase.invoke().onSuccess {
                _userData.value = it
            }.onFailure { e ->
                Log.e(TAG, e.toString())
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}