package ru.androidlearning.moviesearch.view

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MainFragmentBinding
import ru.androidlearning.moviesearch.model.MovieDetails
import ru.androidlearning.moviesearch.viewmodel.AppState
import ru.androidlearning.moviesearch.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val mainFragmentBinding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return mainFragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getMovieDetailsLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMovieDetailsFromLocalSource()

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val moveDetails = appState.movieDetails
                mainFragmentBinding.loadingLayout.visibility = View.GONE
                setData(moveDetails)
            }
            is AppState.Error -> {
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder
                    .setTitle(getString(R.string.attentionWord))
                    .setMessage(getString(R.string.errorLoadingMoveDetailsMessage))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.tryToReloadButtonText)) { dialog, which -> run { viewModel.getMovieDetailsFromLocalSource() } }
                    .setNegativeButton(getString(R.string.resurtnToPrevScreenButtonText)) { dialog, which ->
                        run {
                            //fragmentManager?.popBackStack()
                        }
                    }
                    .create().show()

            }
            is AppState.Loading -> {
                mainFragmentBinding.loadingLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun setData(movieDetails: MovieDetails) {
        mainFragmentBinding.moveName.text = movieDetails.movie.name

        mainFragmentBinding.moveGenre.text =
            String.format(Locale.getDefault(), getString(R.string.genreWord) + movieDetails.genre)

        mainFragmentBinding.moveDuration.text = String.format(
            Locale.getDefault(),
            getString(R.string.durationWord) + movieDetails.durationInMinutes.toString() + " " + getString(R.string.minutesWord)
        )

        mainFragmentBinding.moveRating.text = String.format(
            Locale.getDefault(),
            getString(R.string.ratingWord) + movieDetails.movie.rating.toString())


        mainFragmentBinding.moveReleaseDate.text =
            String.format(
                Locale.getDefault(),
                getString(R.string.releaseDateWord) + movieDetails.movie.releaseDate?.let { getStringFromDate(it) })


        mainFragmentBinding.moveDescription.text = movieDetails.description

    }

    private fun getStringFromDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return sdf.format(date)
    }

}