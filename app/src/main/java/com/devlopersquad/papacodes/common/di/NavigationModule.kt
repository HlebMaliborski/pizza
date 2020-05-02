package com.devlopersquad.papacodes.common.di

import com.devlopersquad.papacodes.common.navigation.Navigator
import com.devlopersquad.papacodes.common.navigation.NavigatorImpl
import org.koin.dsl.module

val navigationModule = module {
    single<Navigator> { get<NavigatorImpl>() }
}