package com.example.papacodes.common.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController

class NavigatorImpl(private val navController: NavController, private val context: Context) :
    Navigator {
    override fun processCode() {
        val uris = Uri.parse("https://new.papajohns.by/")
        val intents = Intent(Intent.ACTION_VIEW, uris)
        context.startActivity(intents)
    }

    override fun openCodeScreen() {
        TODO("Not yet implemented")
    }
}

interface Navigator {
    fun processCode()
    fun openCodeScreen()
}