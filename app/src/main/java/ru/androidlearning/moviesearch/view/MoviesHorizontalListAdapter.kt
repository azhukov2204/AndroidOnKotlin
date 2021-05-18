package ru.androidlearning.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.model.Movie

class MoviesHorizontalListAdapter(private val onMovieItemClickListener: MoviesSearchFragmentAdapter.OnMovieItemClickListener?) :
        RecyclerView.Adapter<MoviesHorizontalListAdapter.MoviesHorizontalListHolder>() {

    private var filteredMoviesList = listOf<Movie>()
    private lateinit var moveCategory: String

    fun setData(filteredMoviesList: List<Movie>, movesCategory: String) {
        this.moveCategory = movesCategory
        this.filteredMoviesList = filteredMoviesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHorizontalListHolder = MoviesHorizontalListHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))

    override fun onBindViewHolder(holder: MoviesHorizontalListHolder, position: Int) {
        holder.bind(filteredMoviesList[position])
    }

    override fun getItemCount(): Int = filteredMoviesList.size

    inner class MoviesHorizontalListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) = with(itemView) {
            findViewById<TextView>(R.id.movie_name).text = movie.title
            findViewById<TextView>(R.id.movie_genre).text = movie.genre
            findViewById<TextView>(R.id.movie_rating).text = movie.rating.toString()
            setOnClickListener { onMovieItemClickListener?.onMovieItemClick(movie) }
        }
    }
}
