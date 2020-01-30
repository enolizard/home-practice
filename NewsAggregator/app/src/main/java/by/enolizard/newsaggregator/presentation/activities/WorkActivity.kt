package by.enolizard.newsaggregator.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import by.enolizard.newsaggregator.R
import by.enolizard.newsaggregator.presentation.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class WorkActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // else нужно ждать вызова onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds =
            listOf(R.navigation.news, R.navigation.favorite, R.navigation.settings)

        // настройка bottom navigation view c списоком navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }
}
