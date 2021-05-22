package ru.androidlearning.moviesearch.view.history

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MoviesHistoryFragmentBinding
import ru.androidlearning.moviesearch.repository.db.MovieEntity
import ru.androidlearning.moviesearch.utils.showSnackBar
import ru.androidlearning.moviesearch.view.MainActivity
import ru.androidlearning.moviesearch.viewmodel.MoviesHistoryLoadState
import ru.androidlearning.moviesearch.viewmodel.MoviesHistoryViewModel

const val ERROR_LOADING_MOVIES_HISTORY_MESSAGE = "Error loading movies history"

class MoviesHistoryFragment : Fragment() {
    private var _binding: MoviesHistoryFragmentBinding? = null
    private val movieHistoryFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val moviesHistoryViewModel: MoviesHistoryViewModel by lazy {
        ViewModelProvider(this).get(MoviesHistoryViewModel::class.java)
    }
    private val movieHistoryFragmentAdapter = MovieHistoryFragmentAdapter()

    companion object {
        @JvmStatic
        fun newInstance() = MoviesHistoryFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesHistoryFragmentBinding.inflate(inflater, container, false)
        mainActivity.hideHomeButton()
        setHasOptionsMenu(true) //используем меню
        return movieHistoryFragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieHistoryFragmentBinding.moviesHistoryRecyclerView.adapter = movieHistoryFragmentAdapter
        moviesHistoryViewModel.moviesHistoryLiveData.observe(viewLifecycleOwner) {
            renderMoviesHistoryData(it)
        }
        moviesHistoryViewModel.getMoviesHistoryFromDB()
    }

    private fun renderMoviesHistoryData(moviesHistoryLoadState: MoviesHistoryLoadState) {
        when (moviesHistoryLoadState) {
            is MoviesHistoryLoadState.Success -> onSuccessAction(moviesHistoryLoadState.movieEntities)
            is MoviesHistoryLoadState.Error -> onErrorAction(moviesHistoryLoadState.error.message)
            is MoviesHistoryLoadState.Loading -> onLoadingAction()
        }
    }

    private fun onSuccessAction(movieEntities: List<MovieEntity>) {
        movieHistoryFragmentBinding.movieSearchFragmentLoadingLayout.visibility = View.GONE
        movieHistoryFragmentAdapter.setData(movieEntities)
    }

    private fun onLoadingAction() {
        movieHistoryFragmentBinding.movieSearchFragmentLoadingLayout.visibility = View.VISIBLE
    }

    private fun onErrorAction(errorMessage: String?) {
        val message = "$ERROR_LOADING_MOVIES_HISTORY_MESSAGE: $errorMessage"
        movieHistoryFragmentBinding.movieSearchFragmentLoadingLayout.showSnackBar(message)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movies_history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_history -> {
                moviesHistoryViewModel.clearMoviesHistory()
                movieHistoryFragmentAdapter.setData(listOf()) //пока пойдем простым путем, без обратной связи по факту удаления
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }
}