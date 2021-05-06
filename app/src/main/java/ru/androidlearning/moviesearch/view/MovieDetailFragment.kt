package ru.androidlearning.moviesearch.view

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MovieDetailFragmentBinding
import ru.androidlearning.moviesearch.model.Movie
import ru.androidlearning.moviesearch.viewmodel.AppState
import ru.androidlearning.moviesearch.viewmodel.MovieDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailFragment : Fragment() {
    private var _binding: MovieDetailFragmentBinding? = null
    private val movieDetailFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private lateinit var viewModel: MovieDetailViewModel

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
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        viewModel.getMovieDetailsLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMoviesFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> onSuccessAction(appState.movie)
            is AppState.Error -> onErrorAction(appState.error.message)
            is AppState.Loading -> onLoadingAction()
        }
    }

    private fun onLoadingAction() {
        movieDetailFragmentBinding.loadingLayout.visibility = View.VISIBLE
    }

    private fun onErrorAction(message: String?) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder
            .setTitle(getString(R.string.errorWord))
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.tryToReloadButtonText)) { _, _ -> run { viewModel.getMoviesFromLocalSource() } }
            .setNegativeButton(getString(R.string.resurtnToPrevScreenButtonText)) { _, _ ->
                run {
                    fragmentManager?.popBackStack()
                }
            }
            .create().show()
    }

    private fun onSuccessAction(movie: Movie) {
        movieDetailFragmentBinding.loadingLayout.visibility = View.GONE
        setData(movie)
    }

    private fun setData(movie: Movie) {
        movieDetailFragmentBinding.movieName.text = movie.name
        movieDetailFragmentBinding.movieGenre.text =
            String.format(Locale.getDefault(), getString(R.string.genreWord) + movie.genre)
        movieDetailFragmentBinding.movieDuration.text = String.format(
            Locale.getDefault(),
            getString(R.string.durationWord) + movie.durationInMinutes.toString() + " " + getString(
                R.string.minutesWord
            )
        )
        movieDetailFragmentBinding.movieRating.text = String.format(
            Locale.getDefault(),
            getString(R.string.ratingWord) + movie.rating.toString()
        )
        movieDetailFragmentBinding.movieReleaseDate.text =
            String.format(
                Locale.getDefault(),
                getString(R.string.releaseDateWord) + movie.releaseDate?.let {
                    getStringFromDate(
                        it
                    )
                })
        movieDetailFragmentBinding.movieDescription.text = movie.description
    }

    private fun getStringFromDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return sdf.format(date)
    }
}
