package ru.androidlearning.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.model.Movie

class MoviesSearchFragmentAdapter :
    RecyclerView.Adapter<MoviesSearchFragmentAdapter.MoviesSearchFragmentHolder>() {
    private var moviesList = listOf<Movie>()
    private val movieCategoriesList: MutableList<String> = arrayListOf()

    fun setMoviesList(moviesList: List<Movie>) {
        this.moviesList = moviesList
        val categoriesSet: MutableSet<String> = hashSetOf()
        for (movie in moviesList) {
            categoriesSet.add(movie.category)
        }
        this.movieCategoriesList.addAll(categoriesSet)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesSearchFragmentHolder {
        return MoviesSearchFragmentHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movies_horizontal_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesSearchFragmentHolder, position: Int) {
        holder.bind(movieCategoriesList[position],  moviesList)
    }

    override fun getItemCount(): Int = movieCategoriesList.size

    inner class MoviesSearchFragmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(moveCategory: String, moviesList: List<Movie>) {
            itemView.findViewById<TextView>(R.id.movieCategoryTextView).text = moveCategory
            val moviesListHorizontalListAdapter = MoviesHorizontalListAdapter()
            itemView.findViewById<RecyclerView>(R.id.movieHorizontalRecyclerView).adapter=moviesListHorizontalListAdapter
            moviesListHorizontalListAdapter.setData(moviesList, moveCategory)
        }
    }
}