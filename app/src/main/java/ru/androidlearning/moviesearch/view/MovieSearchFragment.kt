package ru.androidlearning.moviesearch.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MovieSearchFragmentBinding
import ru.androidlearning.moviesearch.model.Movie
import ru.androidlearning.moviesearch.viewmodel.AppState
import ru.androidlearning.moviesearch.viewmodel.MovieSearchViewModel

class MovieSearchFragment : Fragment() {
    private var _binding: MovieSearchFragmentBinding? = null
    private val movieSearchFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val movieSearchFragmentAdapter = MovieSearchFragmentAdapter()
    private lateinit var movieSearchViewModel: MovieSearchViewModel

    companion object {
        @JvmStatic
        fun newInstance() =
            MovieSearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieSearchFragmentBinding.inflate(inflater, container, false)
        mainActivity.hideHomeButton()
        initView()
        return movieSearchFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieSearchFragmentBinding.movieSearchRecyclerView.adapter = movieSearchFragmentAdapter
        movieSearchViewModel = ViewModelProvider(this).get(MovieSearchViewModel::class.java)
        movieSearchViewModel.getMovieDetailsLiveData().observe(viewLifecycleOwner, { renderData(it) })
        movieSearchViewModel.getMoviesFromLocalSource()
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> onSuccessAction(appState.movies)
            is AppState.Error -> onErrorAction(appState.error.message)
            is AppState.Loading -> onLoadingAction()
        }

    }

    private fun onLoadingAction() {
        movieSearchFragmentBinding.movieSearchFragmentLoadingLayout.visibility = View.VISIBLE
    }

    private fun onErrorAction(message: String?) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder
            .setTitle(getString(R.string.errorWord))
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.tryToReloadButtonText)) { _, _ -> run { movieSearchViewModel.getMoviesFromLocalSource() } }
            .setNegativeButton(getString(R.string.resurtnToPrevScreenButtonText)) { _, _ ->
                run {
                    fragmentManager?.popBackStack()
                }
            }
            .create().show()
    }

    private fun onSuccessAction(movies: List<Movie>) {
        movieSearchFragmentBinding.movieSearchFragmentLoadingLayout.visibility = View.GONE
        movieSearchFragmentAdapter.setMoviesList(movies)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        /*movieSearchFragmentBinding.openMovieDetailsButton.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.container, MovieDetailFragment.newInstance())
                ?.commit()
        }*/
    }
}
