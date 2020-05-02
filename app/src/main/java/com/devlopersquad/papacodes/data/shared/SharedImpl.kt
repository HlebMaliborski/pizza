package com.devlopersquad.papacodes.data.shared

import android.content.SharedPreferences

private const val TUTORIAL_PASSED_KEY = "tutorialPassed"

class SharedImpl(private val sharedPreferences: SharedPreferences) : Shared {
    override suspend fun tutorialPassed() {
        sharedPreferences.edit().putBoolean(TUTORIAL_PASSED_KEY, true).apply()
    }

    override suspend fun isTutorialPassed(): Boolean {
        return sharedPreferences.getBoolean(TUTORIAL_PASSED_KEY, false)
    }
}

interface Shared {
    suspend fun tutorialPassed()
    suspend fun isTutorialPassed(): Boolean
}