package com.emanuellecarvalho.mvvm.repositories

import com.emanuellecarvalho.mvvm.rest.RetrofitService

class MainRepository (private val retrofitService: RetrofitService) {

    fun getAllLives() = retrofitService.getAllLives()
}