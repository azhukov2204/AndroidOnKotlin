package ru.androidlearning.moviesearch.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MoviesSearchFragmentBinding
import ru.androidlearning.moviesearch.model.Movie
import ru.androidlearning.moviesearch.viewmodel.AppState
import ru.androidlearning.moviesearch.viewmodel.MovieSearchViewModel

class MovieSearchFragment : Fragment() {
    private var _binding: MoviesSearchFragmentBinding? = null
    private val movieSearchFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val moviesSearchViewModel: MovieSearchViewModel by lazy {
        ViewModelProvider(this).get(MovieSearchViewModel::class.java)
    }
    private val moviesSearchFragmentAdapter = MoviesSearchFragmentAdapter()
    private val onMovieItemClickListener =
        object : MoviesSearchFragmentAdapter.OnMovieItemClickListener {
            override fun onMovieItemClick(movie: Movie) {
                activity?.supportFragmentManager?.let {
                    val bundle = Bundle().apply { putParcelable(Movie.MOVIE_BUNDLE_KEY, movie) }
                    it.beginTransaction()
                        .add(R.id.container, MovieDetailFragment.newInstance(bundle))
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }
        }

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
        moviesSearchFragmentAdapter.setOnClickListener(onMovieItemClickListener)
        movieSearchFragmentBinding.movieSearchRecyclerView.adapter = moviesSearchFragmentAdapter

        moviesSearchViewModel.run {
            getMovieDetailsLiveData().observe(viewLifecycleOwner, { renderData(it) })
            getMoviesFromLocalSource()
        }
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
        message?.let {
            movieSearchFragmentBinding.movieSearchFragmentLoadingLayout.showSnackBar(
                message,
                getString(R.string.tryToReloadButtonText),
                { moviesSearchViewModel.getMoviesFromLocalSource() })
        }
    }

    private fun onSuccessAction(movies: List<Movie>) {
        movieSearchFragmentBinding.movieSearchFragmentLoadingLayout.visibility = View.GONE
        moviesSearchFragmentAdapter.setMoviesList(movies)
        movieSearchFragmentBinding.movieSearchFragmentMainLayout.showSnackBar(R.string.loadingCompletedSuccessfullyText)
    }

    override fun onDestroyView() {
        moviesSearchFragmentAdapter.removeListener()
        _binding = null
        super.onDestroyView()
    }

    private fun View.showSnackBar(
        message: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, message, length).setAction(actionText, action).show()
    }

    private fun View.showSnackBar(
        messageResource: Int,
        length: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(this, getString(messageResource), length).show()
    }
}
