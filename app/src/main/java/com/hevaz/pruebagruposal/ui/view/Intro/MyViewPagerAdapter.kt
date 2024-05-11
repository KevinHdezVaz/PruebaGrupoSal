package com.kevin.pruebas.ui.main.intro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.hevaz.pruebagruposal.R

class MyViewPagerAdapter : RecyclerView.Adapter<MyViewPagerAdapter.MyViewPagerViewHolder>() {
    inner class MyViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewPage: TextView = itemView.findViewById(R.id.tv_eachPage)
        val descriptiona: TextView = itemView.findViewById(R.id.descriptionTexto)

        val animacionLottie: LottieAnimationView = itemView.findViewById(R.id.animacionLottie)

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewPagerViewHolder {
        return MyViewPagerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_each_page,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewPagerViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.animacionLottie.setAnimation(R.raw.animation)
                holder.textViewPage.text = "Prueba tecnica Android"

                holder.descriptiona.text =  "Presentada por el Ing. Kevin Hernandez"

            }
            1 -> {
                holder.animacionLottie.setAnimation(R.raw.developer)
                holder.textViewPage.text = "Bienvenido"
                holder.descriptiona.text = "Realiza una consulta a la api Reqres.in y guarda los datos en Room, además de diversas funciones tambien.\n"

            }
            else -> {
                holder.animacionLottie.setAnimation(R.raw.android)
                holder.textViewPage.text = "Busqueda, CRUD y consumo de datos"
                holder.descriptiona.text = "El éxito no llega de la noche a la mañana. \nEs el resultado del trabajo duro, la dedicación y la perseverancia."

            }
        }
    }

    override fun getItemCount(): Int = 3
}