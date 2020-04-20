package com.example.papacodes.data.repository

import com.example.papacodes.common.response.Either
import com.example.papacodes.common.response.Failure
import com.example.papacodes.data.datasource.SharedDataSource
import com.example.papacodes.domain.repository.SharedRepository

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