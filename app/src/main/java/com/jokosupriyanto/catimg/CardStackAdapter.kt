package com.jokosupriyanto.catimg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class CardStackAdapter(
    private var cats: List<Cat> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_cat, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = cats[position]
        holder.name.text = "${spot.id}. ${spot.url}"
        holder.city.text = spot.url
        Glide.with(holder.image)
            .load(spot.url)
            .placeholder(R.drawable.ic_three_dots)
            .error(R.drawable.nopic)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    fun setSpots(spots: List<Cat>) {
        this.cats = spots
    }

    fun getSpots(): List<Cat> {
        return cats
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}