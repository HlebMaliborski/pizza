package com.example.papacodes.data.datasource

import com.example.core_common.result.Failure
import com.example.papacodes.common.response.Either
import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseDataSource {
    protected fun <T> runRequest(
        response: Response<T>
    ): Either<Failure, T> {
        return try {
            when (response.isSuccessful) {
                true -> {
                    response.body()?.let {
                        Either.Success(it)
                    } ?: Either.Error(Failure.NetworkFailure.EmptyNetworkResponse)
                }
                false -> Either.Error(
                    Failure.NetworkFailure.ServerError(
                        response.code().toString()
                    )
                )
            }
        } catch (error: Throwable) {
            Either.Error(Failure.NetworkFailure.NetworkConnection)
        }
    }
}

inline fun <R> runRequestTryCatch(action: () -> Either<Failure, R>): Either<Failure, R> {
    return try {
        action()
    } catch (error: UnknownHostException) {
        Either.Error(Failure.NetworkFailure.UnknownHostFailure)
    } catch (error: Exception) {
        Either.Error(Failure.NetworkFailure.NetworkConnection)
    }
}