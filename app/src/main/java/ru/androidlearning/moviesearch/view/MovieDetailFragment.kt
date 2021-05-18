package ru.androidlearning.moviesearch.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.common_functions.getStringFromDate
import ru.androidlearning.moviesearch.databinding.MovieDetailFragmentBinding
import ru.androidlearning.moviesearch.model.Movie
import ru.androidlearning.moviesearch.model.MovieDetailsDTO
import ru.androidlearning.moviesearch.model.MovieDetailsLoader
import java.util.*

class MovieDetailFragment : Fragment() {
    private var _binding: MovieDetailFragmentBinding? = null
    private val movieDetailFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private var movie: Movie? = null
    private lateinit var movieDetailsLoader: MovieDetailsLoader
    private val movieDetailsLoaderListener = object : MovieDetailsLoader.MovieDetailsLoaderListener {
        override fun onSuccess(movieDetailsDTO: MovieDetailsDTO?) {
            movie?.durationInMinutes = movieDetailsDTO?.runtime  //когда полей будет больше - сделаю отдельный метод для маппинга
            movie?.let { setData(it) }
        }
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onFailed(throwable: Throwable) {
            throwable.message?.let { message ->
                movieDetailFragmentBinding.movieDetailFragmentLoadingLayout.showSnackBar(
                    message,
                    getString(R.string.tryToReloadButtonText),
                    { movie?.id?.let { movieDetailsLoader.loadDetailsList(it) } })
            }
        }
    }

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movie = arguments?.getParcelable(Movie.MOVIE_BUNDLE_KEY)
        movieDetailsLoader = MovieDetailsLoader(movieDetailsLoaderListener)
        movie?.id?.let { movieDetailsLoader.loadDetailsList(it) }
    }

    private fun setData(movie: Movie) = with(movieDetailFragmentBinding) {
        movieDetailFragmentLoadingLayout.visibility = View.GONE
        movieName.text = movie.title
        movieGenre.text =
            String.format(Locale.getDefault(), getString(R.string.genreWord) + movie.genre)
        movieDuration.text = String.format(
            Locale.getDefault(),
            getString(R.string.durationWord) + movie.durationInMinutes.toString() + " " + getString(
                R.string.minutesWord
            )
        )
        movieRating.text = String.format(
            Locale.getDefault(),
            getString(R.string.ratingWord) + movie.rating.toString()
        )
        movieReleaseDate.text = String.format(
            Locale.getDefault(),
            getString(R.string.releaseDateWord) + movie.releaseDate?.let { getStringFromDate(it) })
        movieDetailFragmentBinding.movieDescription.text = movie.description
    }

    private fun View.showSnackBar(
        message: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, message, length).setAction(actionText, action).show()
    }
}
