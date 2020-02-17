package com.example.flickerbrowser

import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.math.max

class FlickrImageViewHolder(view : View ): RecyclerView.ViewHolder(view) {
    val thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
    val title = view.findViewById<TextView>(R.id.title)
}

class FlickrRecyclerViewAdapter(var photoList : ArrayList<Photo>) : RecyclerView.Adapter<FlickrImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent,false)
        return FlickrImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return max(photoList.size,1)
    }

    fun loadNewData(newPhotos : ArrayList<Photo>){
        photoList= newPhotos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo?{
        return if(photoList.isEmpty()) return null else photoList[position]
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {

        if( photoList.isEmpty() ){
            holder.thumbnail.setImageResource(R.drawable.placeholder)
            holder.title.setText( R.string.nothing_to_show )
            return
        }

        val p = getPhoto(position)
        Picasso.get().load(p?.image).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.thumbnail)
        holder.title.text = p?.title
    }

}