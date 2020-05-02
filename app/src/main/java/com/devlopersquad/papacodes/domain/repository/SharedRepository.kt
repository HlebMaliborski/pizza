package com.devlopersquad.papacodes.domain.repository

import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.common.response.Failure

interface SharedRepository {
    suspend fun tutorialPassed()
    suspend fun isTutorialPassed(): Either<Failure, Boolean>
}