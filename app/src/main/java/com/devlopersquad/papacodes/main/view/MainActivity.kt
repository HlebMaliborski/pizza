package com.devlopersquad.papacodes.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.devlopersquad.papacodes.R
import com.devlopersquad.papacodes.common.navigation.NavigatorImpl
import com.devlopersquad.papacodes.main.viewmodel.MainViewModel
import com.devlopersquad.papacodes.presentation.extensions.observeViewState
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewState()
    }

    private fun initViewState() {
        observeViewState(mainViewModel.isTutorialPassed) {
            handleViewState(it)
        }
    }

    private fun handleViewState(isTutorialPassed: Boolean) {
        val navController = findNavController(R.id.navHostFragment)
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.main_graph)
        if (isTutorialPassed) {
            graph.startDestination = R.id.codeFragment
        } else {
            graph.startDestination = R.id.codeTutorialFragment
        }
        navController.graph = graph
        declareKoinModules(navController)
    }

    private fun declareKoinModules(navController: NavController) {
        getKoin().declare(NavigatorImpl(navController, this), override = true)
    }
}
