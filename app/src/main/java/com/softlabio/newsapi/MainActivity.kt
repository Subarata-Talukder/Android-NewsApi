package com.softlabio.newsapi

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.softlabio.newsapi.db.NewsDb
import com.softlabio.newsapi.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = NewsRepository(NewsDb(this))
        viewModel = ViewModelProvider(this, viewModelFactory { NewsViewModel(repository) }).get(
            NewsViewModel::class.java
        )


        // bottomNavigationView.setupWithNavController(fragmentContainerView.findNavController())

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }
}