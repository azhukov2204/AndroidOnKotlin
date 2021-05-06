package ru.androidlearning.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.model.Movie

class MoviesHorizontalListAdapter() :
    RecyclerView.Adapter<MoviesHorizontalListAdapter.MoviesHorizontalListHolder>() {

    private var moviesList = listOf<Movie>()
    private lateinit var moveCategory: String

    fun setData(moviesList: List<Movie>, movesCategory: String) {
        this.moveCategory = movesCategory
        this.moviesList = moviesList.filter { it.category == movesCategory }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHorizontalListHolder {
        return MoviesHorizontalListHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesHorizontalListHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size

    inner class MoviesHorizontalListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.movie_name).text = movie.name
            itemView.findViewById<TextView>(R.id.movie_genre).text = movie.genre
            itemView.findViewById<TextView>(R.id.movie_rating).text = movie.rating.toString()
        }
    }
}
