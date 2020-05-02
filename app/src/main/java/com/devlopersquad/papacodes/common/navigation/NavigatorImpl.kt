package com.devlopersquad.papacodes.common.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import com.devlopersquad.papacodes.R

class NavigatorImpl(private val navController: NavController, private val context: Context) :
    Navigator {
    override suspend fun processCode() {
        val uris = Uri.parse("https://new.papajohns.by/")
        val intents = Intent(Intent.ACTION_VIEW, uris)
        context.startActivity(intents)
    }

    override suspend fun openCodeScreen() {
        navController.popBackStack(R.id.codeTutorialFragment, true)
        navController.navigate(R.id.codeFragment)
    }
}

interface Navigator {
    suspend fun processCode()
    suspend fun openCodeScreen()
}