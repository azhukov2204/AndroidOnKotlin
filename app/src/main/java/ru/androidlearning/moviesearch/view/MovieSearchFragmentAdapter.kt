package ru.androidlearning.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MovieListItemBinding
import ru.androidlearning.moviesearch.model.Movie

class MovieSearchFragmentAdapter :
    RecyclerView.Adapter<MovieSearchFragmentAdapter.MovieSearchFragmentHolder>() {

    private var _binding: MovieListItemBinding? = null
    private val movieListItemBinding get() = _binding!!
    private var moviesList = listOf<Movie>()

        fun setMoviesList(moviesList: List<Movie>) {
            this.moviesList = moviesList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchFragmentHolder {

        _binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            movieListItemBinding.movieName.text = movie.name
            movieListItemBinding.movieGenre.text = movie.genre
            movieListItemBinding.movieRating.text = movie.rating.toString()
        }
    }
}