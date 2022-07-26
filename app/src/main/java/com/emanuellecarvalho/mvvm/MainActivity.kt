package com.emanuellecarvalho.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.emanuellecarvalho.mvvm.adapters.MainAdapter
import com.emanuellecarvalho.mvvm.databinding.ActivityMainBinding
import com.emanuellecarvalho.mvvm.repositories.MainRepository
import com.emanuellecarvalho.mvvm.rest.RetrofitService
import com.emanuellecarvalho.mvvm.viewModel.main.MainViewModel
import com.emanuellecarvalho.mvvm.viewModel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Criando a viewModel na MainActivity a partir da MainViewModelFactory
        viewModel =
            ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        // Instancindo o adapter
        binding.recyclerview.adapter = adapter
    }
}