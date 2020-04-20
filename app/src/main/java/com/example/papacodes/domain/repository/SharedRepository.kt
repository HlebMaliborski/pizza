package com.example.papacodes.domain.repository

import com.example.papacodes.common.response.Either
import com.example.papacodes.common.response.Failure

interface SharedRepository {
    suspend fun tutorialPassed()
    suspend fun isTutorialPassed(): Either<Failure, Boolean>
}