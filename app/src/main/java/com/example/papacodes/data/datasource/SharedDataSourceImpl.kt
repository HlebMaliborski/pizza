package com.example.papacodes.data.datasource

import com.example.papacodes.data.shared.Shared

class SharedDataSourceImpl(private val shared: Shared) : SharedDataSource,
    BaseDataSource() {

    override suspend fun tutorialPassed() {
        shared.tutorialPassed()
    }

    override suspend fun isTutorialPassed(): Boolean = shared.isTutorialPassed()
}

interface SharedDataSource {
    suspend fun tutorialPassed()
    suspend fun isTutorialPassed(): Boolean
}