package ru.androidlearning.moviesearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.androidlearning.moviesearch.R
import ru.androidlearning.moviesearch.databinding.MainActivityBinding
import ru.androidlearning.moviesearch.ui.favorite.MoviesFavoriteFragment
import ru.androidlearning.moviesearch.ui.history.MoviesHistoryFragment
import ru.androidlearning.moviesearch.ui.maps.MapsFragment
import ru.androidlearning.moviesearch.ui.phones_book.PhoneBookFragment
import ru.androidlearning.moviesearch.ui.search.MoviesListsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
        if (savedInstanceState == null) {
            openFragment(MoviesListsFragment.newInstance(), getString(R.string.movieListsFragmentTitle))
        }
    }

    private fun initToolBar() {
        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        setSupportActionBar(mainActivityBinding.toolbar)
        val navView: BottomNavigationView = mainActivityBinding.navView

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movieListsFragment -> {
                    openFragment(MoviesListsFragment.newInstance(), getString(R.string.movieListsFragmentTitle))
                    true
                }
                R.id.movieHistoryFragment -> {
                    openFragment(MoviesHistoryFragment.newInstance(), getString(R.string.movieHistoryFragmentTitle))
                    true
                }
                R.id.movieFavoriteFragment -> {
                    openFragment(MoviesFavoriteFragment.newInstance(), getString(R.string.moviesFavoriteFragmentTitle))
                    true
                }
                R.id.phonesBookFragment -> {
                    openFragment(PhoneBookFragment.newInstance(), getString(R.string.phoneBookFragmentTitle))
                    true
                }
                R.id.mapsFragment -> {
                    openFragment(MapsFragment.newInstance())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun <T : Fragment> openFragment(fragmentInstance: T, title: String = getString(R.string.app_name)) {
        clearBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragmentInstance)
            .setTransition(TRANSIT_FRAGMENT_FADE)
            .commitAllowingStateLoss()
        this.title = title
    }

    private fun clearBackStack() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            val entry = fragmentManager.getBackStackEntryAt(0)
            fragmentManager.popBackStack(entry.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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
