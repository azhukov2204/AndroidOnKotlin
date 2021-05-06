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
import ru.androidlearning.moviesearch.databinding.MoviesSearchFragmentBinding
import ru.androidlearning.moviesearch.model.Movie
import ru.androidlearning.moviesearch.viewmodel.AppState
import ru.androidlearning.moviesearch.viewmodel.MovieSearchViewModel

class MovieSearchFragment : Fragment() {
    private var _binding: MoviesSearchFragmentBinding? = null
    private val movieSearchFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var moviesSearchViewModel: MovieSearchViewModel
    private val moviesSearchFragmentAdapter =
        MoviesSearchFragmentAdapter()

    companion object {
        @JvmStatic
        fun newInstance() = MovieSearchFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesSearchFragmentBinding.inflate(inflater, container, false)
        mainActivity.hideHomeButton()
        return movieSearchFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesSearchFragmentAdapter.setOnClickListener(object : MoviesSearchFragmentAdapter.OnMovieItemClickListener {
            override fun onMovieItemClick(movie: Movie) {
                val fragmentManager = activity?.supportFragmentManager
                if (fragmentManager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(Movie.MOVIE_BUNDLE_KEY, movie)
                    fragmentManager.beginTransaction()
                        .add(R.id.container, MovieDetailFragment.newInstance(bundle))
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }
        })
        movieSearchFragmentBinding.movieSearchRecyclerView.adapter = moviesSearchFragmentAdapter
        moviesSearchViewModel = ViewModelProvider(this).get(MovieSearchViewModel::class.java)
        moviesSearchViewModel.getMovieDetailsLiveData()
            .observe(viewLifecycleOwner, { renderData(it) })
        moviesSearchViewModel.getMoviesFromLocalSource()
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
            .setPositiveButton(getString(R.string.tryToReloadButtonText)) { _, _ -> run { moviesSearchViewModel.getMoviesFromLocalSource() } }
            .setNegativeButton(getString(R.string.resurtnToPrevScreenButtonText)) { _, _ ->
                run {
                    fragmentManager?.popBackStack()
                }
            }
            .create().show()
    }

    private fun onSuccessAction(movies: List<Movie>) {
        movieSearchFragmentBinding.movieSearchFragmentLoadingLayout.visibility = View.GONE
        moviesSearchFragmentAdapter.setMoviesList(movies)
    }


    override fun onDestroyView() {
        moviesSearchFragmentAdapter.removeListener()
        _binding = null
        super.onDestroyView()
    }
}
