package com.example.superheroes.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.superheroes.data.Superhero
import com.example.superheroes.data.SuperheroesApiService
import com.example.superheroes.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {


    //Valor creado por demanda de MainActivity Punto:57
    companion object {
        val EXTRA_SUPERHERO_ID = "SUPERHERO_ID"
    }

    lateinit var binding: ActivityDetailBinding

    lateinit var superhero: Superhero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_SUPERHERO_ID, -1) /* <=== Se pone -1 para detectar con ese número algun error*/

        getById(id)
    }

    private fun loadData() {
        binding.nameTextView.text = superhero.name
        Picasso.get().load(superhero.image.url).into(binding.photoImageView)
    }

    private fun getById(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(SuperheroesApiService::class.java)
                val result = apiService.getSuperheroById(id)

                superhero = result

                runOnUiThread {
                    loadData()
                }
                //Log.i("HTTP", "${result.results}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/7252591128153666/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}




/*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}*/