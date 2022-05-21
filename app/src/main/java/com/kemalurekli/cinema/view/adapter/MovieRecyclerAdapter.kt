package com.kemalurekli.cinema.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kemalurekli.cinema.R
import com.kemalurekli.cinema.model.MovieResult
import com.kemalurekli.cinema.view.HomeFragmentDirections
import javax.inject.Inject

class MovieRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movies : List<MovieResult>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return MovieViewHolder(view)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.iv_movie)
        val nameText = holder.itemView.findViewById<TextView>(R.id.tv_movie_name)
        val movieGenreText = holder.itemView.findViewById<TextView>(R.id.tv_movie_genre)
        val descText = holder.itemView.findViewById<TextView>(R.id.tv_movie_plot)
        val movie = movies[position]
        holder.itemView.apply {
            when {
                movie.Poster != "N/A" -> {
                    glide.load(movie.Poster).into(imageView)
                }
                else -> {
                    //Edit for local drawable. URI
                    val defaultPicture = "https://icons-for-free.com/iconfiles/png/512/svg+cinema+doodle+film+film+roll+movie+movie+film+icon-1320165854732110438.png"
                    glide.load(defaultPicture).into(imageView)
                }
            }
            nameText.text = movie.Title
            movieGenreText.text = movie.Genre
            descText.text = movie.Plot
            setOnClickListener {
                Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie.imdbID))
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}