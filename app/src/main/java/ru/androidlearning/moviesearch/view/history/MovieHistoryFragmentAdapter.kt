package ru.androidlearning.moviesearch.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.repository.db.MovieEntity
import ru.androidlearning.moviesearch.view.search.GOOD_RATING
import ru.androidlearning.moviesearch.view.search.MIDDLE_RATING
import ru.androidlearning.moviesearch.view.search.POSTERS_BASE_URL

class MovieHistoryFragmentAdapter : RecyclerView.Adapter<MovieHistoryFragmentAdapter.MovieHistoryFragmentHolder>() {
    private var movieEntitiesList: List<MovieEntity> = listOf()

    fun setData(movieEntitiesList: List<MovieEntity>) {
        this.movieEntitiesList = movieEntitiesList.sortedByDescending { it.viewedDate }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHistoryFragmentHolder {
        return MovieHistoryFragmentHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHistoryFragmentHolder, position: Int) {
        holder.bind(movieEntitiesList[position])
    }

    override fun getItemCount(): Int {
        return movieEntitiesList.size
    }

    inner class MovieHistoryFragmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieEntity: MovieEntity) {
            with(itemView) {
                findViewById<TextView>(R.id.movie_name).text = movieEntity.title
                findViewById<TextView>(R.id.movie_genre).text = movieEntity.genre
                val movieRatingView = findViewById<TextView>(R.id.movie_rating)
                movieRatingView.text = movieEntity.rating.toString()
                Picasso.get().load("$POSTERS_BASE_URL${movieEntity.posterUri}")
                    .into(findViewById<AppCompatImageView>(R.id.movePoster))
                val forAdultTextView = findViewById<TextView>(R.id.for_adult)
                if (movieEntity.isAdult == true) {
                    forAdultTextView.visibility = View.VISIBLE
                } else {
                    forAdultTextView.visibility = View.GONE
                }
                findViewById<TextView>(R.id.viewed_date).text = movieEntity.viewedDate
                movieRatingView.setTextColor(
                    when {
                        movieEntity.rating ?: 0.0 > GOOD_RATING -> {
                            ContextCompat.getColor(context, R.color.green)
                        }
                        movieEntity.rating ?: 0.0 > MIDDLE_RATING -> {
                            ContextCompat.getColor(context, R.color.gray)
                        }
                        else -> {
                            ContextCompat.getColor(context, R.color.red)
                        }
                    }
                )
            }
        }
    }
}
