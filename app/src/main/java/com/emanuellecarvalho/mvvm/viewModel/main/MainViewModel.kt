package com.emanuellecarvalho.mvvm.viewModel.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emanuellecarvalho.mvvm.models.Live
import com.emanuellecarvalho.mvvm.repositories.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// passing repository on constructor to deal with the data from API
class MainViewModel(private val repository : MainRepository) : ViewModel() {

    // livedata
    val liveList = MutableLiveData<List<Live>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllLives() {

        val request = repository.getAllLives()
        request.enqueue(object : Callback<List<Live>> {
            override fun onResponse(call: Call<List<Live>>, response: Response<List<Live>>) {
                Log.i("MANU", "onResponse")
                liveList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Live>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })

    }

}