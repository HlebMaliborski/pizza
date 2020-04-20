package com.example.papacodes.common.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val shareModule = module {
    single { provideSettingsPreferences(androidContext()) }
}

private const val PREFERENCES_KEY = "code_key"

private fun provideSettingsPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)