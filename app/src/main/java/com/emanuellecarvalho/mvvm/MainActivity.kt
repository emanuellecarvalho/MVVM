package com.emanuellecarvalho.mvvm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
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

    private val adapter = MainAdapter { live ->
        openLink(live.link)
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

        // Instanciando o adapter
        binding.recyclerview.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        // Observando a lista do LiveData pra quando ela receber um valor que é quando o postvalue for executado
        //  e vai receber uma lista de lives e a MainActivity vai receber através do Observer e vai mostrar na RecyclerView
        viewModel.liveList.observe(this, Observer { lives ->
            Log.i("AAAAA", "onStart")
            adapter.setLiveList(lives)
        })
        // Qaundo der falha, ele pega a mensagem de erro e mostra na UI
        viewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        // A tela vai ser atualizada toda vez que o usuário estiver nela
        viewModel.getAllLives()
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

}