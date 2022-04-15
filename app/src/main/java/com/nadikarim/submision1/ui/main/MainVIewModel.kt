package com.nadikarim.submision1.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.nadikarim.submision1.data.model.stories.StoriesResponse
import com.nadikarim.submision1.data.model.stories.Story
import com.nadikarim.submision1.data.remote.ApiConfig
import com.nadikarim.submision1.utils.RETROFIT_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainVIewModel() : ViewModel(){

    private val _listStory = MutableLiveData<ArrayList<Story>>()
    val listStory: LiveData<ArrayList<Story>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    fun getAllStories(auth: String) {
        _isLoading.value = true
        ApiConfig().getApiService().getListStory("Bearer $auth")
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listStory.postValue(response.body()?.listStory)
                        Log.d(RETROFIT_TAG, response.body()?.listStory.toString())
                    }

                }

                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(RETROFIT_TAG, t.message.toString())
                }

            })
    }


}