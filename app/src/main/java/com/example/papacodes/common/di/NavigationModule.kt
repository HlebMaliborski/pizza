package com.example.papacodes.common.di

import com.example.papacodes.common.navigation.Navigator
import com.example.papacodes.common.navigation.NavigatorImpl
import org.koin.dsl.module

val navigationModule = module {
    single<Navigator> { get<NavigatorImpl>() }
}