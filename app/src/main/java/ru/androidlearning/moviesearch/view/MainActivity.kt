package ru.androidlearning.moviesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.AppBarMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarMainBinding: AppBarMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appBarMainBinding = AppBarMainBinding.inflate(layoutInflater)

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
