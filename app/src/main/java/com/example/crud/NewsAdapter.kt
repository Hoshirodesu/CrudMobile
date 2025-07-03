package com.example.crud

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(
    private val newsList: List<News>,
    private val onItemClick: (News) -> Unit,
    private val onDeleteClick: (News) -> Unit
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gambar = view.findViewById<ImageView>(R.id.imgNews)
        val judul = view.findViewById<TextView>(R.id.titleText)
        val shortDesc = view.findViewById<TextView>(R.id.shortDescText)
        val desc = view.findViewById<TextView>(R.id.descText)
        val deleteIcon = view.findViewById<ImageView>(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.judul.text = news.title
        holder.shortDesc.text = news.short_desc
        holder.desc.text = news.desc
        Glide.with(holder.itemView.context).load(news.img).into(holder.gambar)

        holder.itemView.setOnClickListener {
            onItemClick(news)
        }
        holder.deleteIcon.setOnClickListener {
            onDeleteClick(news)
        }
    }

    override fun getItemCount() = newsList.size
}