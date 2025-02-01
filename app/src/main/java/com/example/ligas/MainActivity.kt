package com.example.ligas

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.ligas.adapter.LigasAdapter
import com.example.ligas.databinding.ActivityMainBinding
import com.example.ligas.model.Liga
import com.example.ligas.model.LigaJSON
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONArray

class MainActivity : AppCompatActivity(), LigasAdapter.OnLigaListener {
    private lateinit var binding: ActivityMainBinding
    //Me creo un objeto del adaptador
    private lateinit var adapter: LigasAdapter
    // Creamos ArrayList
    private lateinit var listaLigas: ArrayList<LigaJSON>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instancias()

        val peticion: JsonObjectRequest = JsonObjectRequest("https://www.thesportsdb.com/api/v1/json/3/all_leagues.php", {
           // Log.v("datos", it.toString())
            val array: JSONArray = it.getJSONArray("leagues")
            val gson = Gson()
            for (i in 0..array.length()-1){
                val leagueJSON = array.getJSONObject(i)
                val league = gson.fromJson(leagueJSON.toString(), LigaJSON::class.java)
                if (league.strSport.equals("soccer",true)) {
                    adapter.addLeague(league) // Para pintar todas las ligas
                }
                // Ojeto JSON -> objeto kotlin GSON
                //Log.v("datos", leagueJSON.getString("strLeague"))
            }
            }, { Log.v("datos", it.toString())})
        Volley.newRequestQueue(applicationContext).add(peticion)

    }

    private fun instancias() {
       /* listaLigas = arrayListOf(
            Liga("BBVA",R.drawable.liga,90),
            Liga("Bundesliga",R.drawable.icono,70),
            Liga("Calcio",R.drawable.icono,65),
            Liga("Premier",R.drawable.premier,90),
            Liga("Ligue1",R.drawable.icono,80),)*/
        listaLigas = ArrayList() // Hemos creado un Array vacio y se ha pasado al adapter en la siguiente linea
        adapter = LigasAdapter(listaLigas, this) // Hay que pasarle ArrayList y el contexto al igual que en Ligas Adapter, por eso creamo la ArrayList
        // Asociar Recycler view con adaptador
        binding.recyclerLigas.adapter = adapter
        if (resources.configuration.orientation == 1) {
            binding.recyclerLigas.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } else if (resources.configuration.orientation == 2) {
            binding.recyclerLigas.layoutManager =
                GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun onLigaSelected(liga: Liga) {
        Snackbar.make(binding.root,"Liga comunicada con exito ${liga.nombre}",
            Snackbar.LENGTH_SHORT).show()
    }
}