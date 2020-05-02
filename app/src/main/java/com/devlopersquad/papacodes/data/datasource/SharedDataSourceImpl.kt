package com.devlopersquad.papacodes.data.datasource

import com.devlopersquad.papacodes.data.shared.Shared

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