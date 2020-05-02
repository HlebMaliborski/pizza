package com.devlopersquad.papacodes.common.di

import com.devlopersquad.papacodes.common.network.NetworkManager
import com.devlopersquad.papacodes.common.network.NetworkManagerImpl
import org.koin.dsl.module

val utilModule = module {
    single<NetworkManager> { NetworkManagerImpl(context = get()) }
}