package com.nadikarim.submision1.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.nadikarim.submision1.data.model.login.LoginResponse
import com.nadikarim.submision1.data.model.login.LoginResult
import com.nadikarim.submision1.data.remote.ApiConfig
import com.nadikarim.submision1.utils.RETROFIT_TAG
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _userLogin = MutableLiveData<LoginResult>()
    val userLogin: LiveData<LoginResult> = _userLogin

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        ApiConfig().getApiService().loginUser(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _toastMessage.value = response.body()?.message
                        _userLogin.value = response.body()?.loginResult
                        Log.d(RETROFIT_TAG, response.body()?.message.toString())
                        Log.d(RETROFIT_TAG, response.body()?.loginResult?.token.toString())
                        Log.d(RETROFIT_TAG, response.body()?.loginResult?.name ?: "name")
                        Log.d(RETROFIT_TAG, response.body()?.loginResult?.userId ?: "userId")
                    }
                    if (!response.isSuccessful) {
                        _toastMessage.value = response.message()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _toastMessage.value = t.message
                    _isLoading.value = false
                    Log.d(RETROFIT_TAG, t.message.toString())
                }

            })
    }

}