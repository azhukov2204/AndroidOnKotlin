package ru.androidlearning.moviesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.AppBarMainBinding
import ru.androidlearning.moviesearch.databinding.MainActivityBinding
import ru.androidlearning.moviesearch.databinding.MovieDetailFragmentBinding
import ru.androidlearning.moviesearch.databinding.MovieSearchFragmentBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: MainActivityBinding
    private lateinit var appBarMainBinding: AppBarMainBinding
    private lateinit var movieDetailFragmentBinding: MovieDetailFragmentBinding
    private lateinit var movieSearchFragmentBinding: MovieSearchFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        appBarMainBinding = AppBarMainBinding.inflate(layoutInflater)
        movieDetailFragmentBinding = MovieDetailFragmentBinding.inflate(layoutInflater)
        movieSearchFragmentBinding = MovieSearchFragmentBinding.inflate(layoutInflater)

        setContentView(appBarMainBinding.root)
        initToolBar()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieSearchFragment.newInstance())
                .commitNow()
        }
    }

    private fun initToolBar() {
        setSupportActionBar(appBarMainBinding.toolbar)
    }

    fun showHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    fun hideHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
