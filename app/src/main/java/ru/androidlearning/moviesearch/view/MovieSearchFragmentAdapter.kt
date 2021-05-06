package ru.androidlearning.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.model.Movie

class MovieSearchFragmentAdapter :
    RecyclerView.Adapter<MovieSearchFragmentAdapter.MovieSearchFragmentHolder>() {

    private var moviesList = listOf<Movie>()

    fun setMoviesList(moviesList: List<Movie>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchFragmentHolder {
        return MovieSearchFragmentHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieSearchFragmentHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size


    inner class MovieSearchFragmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.movie_name).text = movie.name
            itemView.findViewById<TextView>(R.id.movie_genre).text = movie.genre
            itemView.findViewById<TextView>(R.id.movie_rating).text = movie.rating.toString()
        }
    }
}