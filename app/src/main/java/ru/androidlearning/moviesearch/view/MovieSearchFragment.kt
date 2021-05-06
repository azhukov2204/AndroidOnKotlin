package ru.androidlearning.moviesearch.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MovieDetailFragmentBinding
import ru.androidlearning.moviesearch.databinding.MovieSearchFragmentBinding

class MovieSearchFragment : Fragment() {
    private var _binding: MovieSearchFragmentBinding? = null
    private val movieSearchFragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MovieSearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initView() {
        movieSearchFragmentBinding.openMovieDetailsButton.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.container, MovieDetailFragment.newInstance())
                ?.commit()
        }
    }
}
