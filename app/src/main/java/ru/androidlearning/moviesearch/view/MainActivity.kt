package ru.androidlearning.moviesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.AppBarMainBinding
import ru.androidlearning.moviesearch.view.search.MovieSearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var appBarMainBinding: AppBarMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MovieSearchFragment.newInstance())
                    .commitNow()
        }
    }

    private fun initToolBar() {
        appBarMainBinding = AppBarMainBinding.inflate(layoutInflater).also { AppBarMainBinding ->
            setContentView(AppBarMainBinding.root)
            setSupportActionBar(AppBarMainBinding.toolbar)
        }
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
