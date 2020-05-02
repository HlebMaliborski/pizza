package com.devlopersquad.papacodes.data.repository

import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.data.datasource.SharedDataSource
import com.devlopersquad.papacodes.domain.repository.SharedRepository

class SharedRepositoryImpl(private val sharedDataSource: SharedDataSource) : SharedRepository {
    override suspend fun tutorialPassed() {
        sharedDataSource.tutorialPassed()
    }

    override suspend fun isTutorialPassed(): Either<Failure, Boolean> {
        return when (val result = sharedDataSource.isTutorialPassed()) {
            true -> Either.Success(result)
            false -> Either.Error(Failure.SharedPreferencesFailure)
        }
    }
}