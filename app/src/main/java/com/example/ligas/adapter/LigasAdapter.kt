package com.example.ligas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ligas.R
import com.example.ligas.model.Liga
import com.example.ligas.model.LigaJSON
import com.google.android.material.snackbar.Snackbar

class LigasAdapter(var lista: ArrayList<LigaJSON>, var context: Context): RecyclerView.Adapter<LigasAdapter.MyHolder>() { //El contexto es la pantalla

    // Objeto de OnLigaListener
    private lateinit var listener: OnLigaListener

    // init se ejecuta sempre despues del constructor(class)
    init {
        // listener = MainActivity as OnLigaListener, es necesario implementarlo en el Main
        listener = context as OnLigaListener
    }

    // itemView -> El XML pasado a vista con todos los elemento comunes a la fila
    inner class MyHolder(itemView: View) :RecyclerView.ViewHolder(itemView) { //inner class es una clase anidada, es decir una clase dentro de otra clase. Esta clase es la que representa el patron. Es decir, ucomo una plantilla e utilizan todas las filas
        // Sacar cada uno de los elementos de la vista
        val imagen: ImageView = itemView.findViewById(R.id.imagenLiga)
        val nombre: TextView = itemView.findViewById(R.id.nombreLiga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        // asociar el XML al holder
        val vista:View = LayoutInflater.from(context).inflate(R.layout.item_liga,parent,false) //Con esto referenciamos el XML
        return MyHolder(vista)

    }

    override fun getItemCount(): Int {
        // cuantas filas tengo que pintar
        return lista.size // Retornamos la lista (Arraylist) con size que eso es para el numero de ligas que haya
    }


    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        // pintara individualmente cada fila con su dato correpondiente
        val liga: LigaJSON = lista[position]
        //holder.imagen.setImageResource(liga.imagen)
        holder.nombre.text = liga.strLeague
        holder.imagen.setOnClickListener{
            /*Snackbar.make(holder.imagen,"La cantidad de interes que suscita la liga ${liga.nombre} es ${liga.calificacion}",
                Snackbar.LENGTH_SHORT).show()*/
            //listener.onLigaSelected(liga) // Para poder usarlo hay que crearle un objeto, sin el objeto el metodo no se puede usar
        }
        /*holder.imagen.setOnLongClickListener { // Es una funcion que tiene que retornar un boolean, por eso se hace el return
            lista.removeAt(position)
            notifyItemRemoved(position) //Siempre que se haga un cambio sobre la lista hay que usar el notify
            return@setOnLongClickListener true
        }*/
    }

    //Metodo para añadir una liga de LigaJson a la lista
    fun addLeague(liga: LigaJSON){
        this.lista.add(liga)
        notifyItemInserted(lista.size-1) // modifica aquella cosa que haya insertado
        //notifyDataSetChanged() // modifica toda la lista
    }


    interface OnLigaListener{
        fun onLigaSelected(liga: Liga)
    }
}