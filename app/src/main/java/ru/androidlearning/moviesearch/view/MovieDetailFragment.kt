package ru.androidlearning.moviesearch.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.common_functions.getStringFromDate
import ru.androidlearning.moviesearch.databinding.MovieDetailFragmentBinding
import ru.androidlearning.moviesearch.model.Movie
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailFragment : Fragment() {
    private var _binding: MovieDetailFragmentBinding? = null
    private val movieDetailFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance(bundle: Bundle) = MovieDetailFragment().apply { arguments = bundle }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        mainActivity.showHomeButton()
        return movieDetailFragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getParcelable<Movie>(Movie.MOVIE_BUNDLE_KEY)?.let { movie -> setData(movie) }
    }

    private fun setData(movie: Movie) = with(movieDetailFragmentBinding) {
        movieName.text = movie.name
        movieGenre.text = String.format(Locale.getDefault(), getString(R.string.genreWord) + movie.genre)
        movieDuration.text = String.format(Locale.getDefault(), getString(R.string.durationWord) + movie.durationInMinutes.toString() + " " + getString(R.string.minutesWord))
        movieRating.text = String.format(Locale.getDefault(), getString(R.string.ratingWord) + movie.rating.toString())
        movieReleaseDate.text = String.format(Locale.getDefault(), getString(R.string.releaseDateWord) + movie.releaseDate?.let { getStringFromDate(it) })
        movieDetailFragmentBinding.movieDescription.text = movie.description
    }
}
