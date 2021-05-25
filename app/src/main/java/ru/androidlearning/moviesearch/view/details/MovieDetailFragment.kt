package ru.androidlearning.moviesearch.view.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MovieDetailFragmentBinding
import ru.androidlearning.moviesearch.model.*
import ru.androidlearning.moviesearch.view.MainActivity
import ru.androidlearning.moviesearch.viewmodel.MovieDetailsLoadState
import ru.androidlearning.moviesearch.viewmodel.MovieDetailsViewModel
import java.util.*

const val ERROR_LOADING_DETAILS_MESSAGE = "Error loading data"
const val POSTERS_BASE_URL = "https://image.tmdb.org/t/p/w200"

class MovieDetailFragment : Fragment() {
    private var _binding: MovieDetailFragmentBinding? = null
    private val movieDetailFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private var movie: Movie? = null
    private val movieDetailsViewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getParcelable(Movie.MOVIE_BUNDLE_KEY)
        movie?.id?.let { movieId ->
            movieDetailsViewModel.run {
                getMovieDetailsLiveData().observe(viewLifecycleOwner) { renderData(it) }
                getMovieDetailsFromServer(movieId, getString(R.string.language))
            }

        }
    }

    private fun renderData(movieDetailsLoadState: MovieDetailsLoadState) {
        when (movieDetailsLoadState) {
            is MovieDetailsLoadState.Success -> onSuccessAction(movieDetailsLoadState.movieDetailsDTO)
            is MovieDetailsLoadState.Error -> onErrorAction(movieDetailsLoadState.error.message)
            is MovieDetailsLoadState.Loading -> onLoadingAction()
        }
    }

    private fun onLoadingAction() {
        movieDetailFragmentBinding.movieDetailFragmentLoadingLayout.visibility = View.VISIBLE
    }

    private fun onErrorAction(errorMessage: String?) {
        val message = "$ERROR_LOADING_DETAILS_MESSAGE: $errorMessage"
        movieDetailFragmentBinding.movieDetailFragmentLoadingLayout.showSnackBar(message)
    }

    private fun onSuccessAction(movieDetailsDTO: MovieDetailsDTO) {
        movie?.durationInMinutes = movieDetailsDTO.runtime
        movie?.let { setData(it) }
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
            getString(R.string.releaseDateWord) + movie.releaseDate)
        movieDescription.text = movie.description
        Picasso.get().load("$POSTERS_BASE_URL${movie.posterUri}").into(movePoster)
    }

    private fun View.showSnackBar(
        message: String,
        length: Int = Snackbar.LENGTH_SHORT,
        actionText: String? = null,
        action: ((View) -> Unit)? = null
    ) {
        Snackbar.make(this, message, length).setAction(actionText, action).show()
    }
}
