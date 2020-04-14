package com.example.papacodes.common.di

import com.example.papacodes.common.network.NetworkManager
import com.example.papacodes.common.network.NetworkManagerImpl
import org.koin.dsl.module

val utilModule = module {
    single<NetworkManager> { NetworkManagerImpl(context = get()) }
}