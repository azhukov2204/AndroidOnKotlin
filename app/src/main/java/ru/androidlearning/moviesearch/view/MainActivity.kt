package ru.androidlearning.moviesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MainActivityBinding
import ru.androidlearning.moviesearch.view.history.MoviesHistoryFragment
import ru.androidlearning.moviesearch.view.search.MovieSearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
        if (savedInstanceState == null) {
            openMovieSearchFragment()
        }
    }

    private fun initToolBar() {
        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        setSupportActionBar(mainActivityBinding.toolbar)
        val navView: BottomNavigationView = mainActivityBinding.navView

        navView.setOnNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.movieSearchFragment -> {
                    openMovieSearchFragment()
                    true
                }
                R.id.movieHistoryFragment -> {
                    openMovieHistoryFragment()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun openMovieHistoryFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MoviesHistoryFragment.newInstance())
            .commitAllowingStateLoss()
    }

    private fun openMovieSearchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MovieSearchFragment.newInstance())
            .commitAllowingStateLoss()
    }

    fun showHomeButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    fun hideHomeButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        hideHomeButton()
        super.onBackPressed()
    }
}
