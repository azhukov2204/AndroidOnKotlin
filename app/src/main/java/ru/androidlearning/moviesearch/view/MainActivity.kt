package ru.androidlearning.moviesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.AppBarMainBinding
import ru.androidlearning.moviesearch.databinding.MainActivityBinding
import ru.androidlearning.moviesearch.databinding.MovieDetailFragmentBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: MainActivityBinding
    private lateinit var appBarMainBinding: AppBarMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        appBarMainBinding = AppBarMainBinding.inflate(layoutInflater)
        setContentView(appBarMainBinding.root)

        initToolBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieDetailFragment.newInstance())
                .commitNow()
        }
    }

    private fun initToolBar() {
        setSupportActionBar(appBarMainBinding.toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeButtonEnabled(true);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}

