package com.example.superheroes.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superheroes.R
import com.example.superheroes.adapters.SuperheroAdapter
import com.example.superheroes.data.Superhero
import com.example.superheroes.data.SuperheroesApiService
import com.example.superheroes.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SuperheroAdapter

    private lateinit var superheroList: List<Superhero>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        superheroList = emptyList() //Esto es para que te pase una lista vacía.

    // Desde aquí .... Copiado desde HOROSCOPO,

        adapter = SuperheroAdapter(superheroList) { position ->
            navigateToDetail(superheroList[position])
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        searchByName("n")
    }
        //  ANTIGUO de arriba (p38): recyclerView.layoutManager = GridLayoutManager(this, 2)
            /*Log.i("EUREKA", "Click en la posicion: $it")
            binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)*/


    private fun navigateToDetail(superhero: Superhero) {
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_SUPERHERO_ID, superhero.id)
        startActivity(intent)
    }


    //Esto de abajo, TAMBIEN Está en HOROSCOPO es el código para añadir
    // la LUPA en la barra de busqueda de la APP.

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val searchViewItem = menu.findItem(R.id.menu_search)
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchByName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }


    private fun searchByName(query: String){
        // Llamada en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(SuperheroesApiService::class.java)
                val result = apiService.findSuperheroesByName(query)

                runOnUiThread {
                    superheroList = result.results //<--- Este codigo es para que ___ llame a la lista
                    adapter.updateData(result.results)
                //Si añado este codigopuedo cambiar al primer heroe a mi nombre o
                // mostrar solo los q tienen "bo" en su nombre:
                    //superheroList.first().name = "Orlando"
                    //superheroList = superheroList.filter { it.name.contains("bo") }
                }
                //Log.i("HTTP", "${result.results}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        /*val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/

        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/7252591128153666/")
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

/*import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}*/