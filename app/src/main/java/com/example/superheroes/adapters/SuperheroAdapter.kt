package com.example.superheroes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes.data.SuperheroesResponse
import com.example.superheroesapp.data.SuperheroResponse
import com.example.superheroesapp.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroAdapter (private var dataSet: List<SuperheroesResponse> = emptyList()) : RecyclerView.Adapter<SuperheroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context))
        return SuperheroViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        holder.render(dataSet[position])
    }

    fun updateData(dataSet: List<SuperheroesResponse>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}

class SuperheroViewHolder(private val binding: ItemSuperheroBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(superhero: SuperheroesResponse) {
        binding.nameTextView.text = superhero.name
        Picasso.get().load (superhero.image.url).into(binding.avatarImagenView)
    }
}