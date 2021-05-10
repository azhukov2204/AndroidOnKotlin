package ru.androidlearning.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.model.Movie

class MoviesHorizontalListAdapter(private val onMovieItemClickListener: MoviesSearchFragmentAdapter.OnMovieItemClickListener?):
    RecyclerView.Adapter<MoviesHorizontalListAdapter.MoviesHorizontalListHolder>() {

    private var filteredMoviesList = listOf<Movie>()
    private lateinit var moveCategory: String

    fun setData(filteredMoviesList: List<Movie>, movesCategory: String) {
        this.moveCategory = movesCategory
        this.filteredMoviesList = filteredMoviesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHorizontalListHolder {
        return MoviesHorizontalListHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesHorizontalListHolder, position: Int) {
        holder.bind(filteredMoviesList[position])
    }

    override fun getItemCount(): Int = filteredMoviesList.size

    inner class MoviesHorizontalListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.movie_name).text = movie.name
            itemView.findViewById<TextView>(R.id.movie_genre).text = movie.genre
            itemView.findViewById<TextView>(R.id.movie_rating).text = movie.rating.toString()
            itemView.setOnClickListener{
                onMovieItemClickListener?.onMovieItemClick(movie)
            }
        }
    }
}
