package com.devlopersquad.papacodes.data.repository

import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.common.network.NetworkManager
import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.data.datasource.CodeCacheDataSource
import com.devlopersquad.papacodes.data.datasource.CodeNetworkDataSource
import com.devlopersquad.papacodes.data.mapper.DataToDomainModelMapper
import com.devlopersquad.papacodes.domain.model.DomainCodeModel
import com.devlopersquad.papacodes.domain.repository.CodeRepository

class CodeRepositoryImpl(
    private val networkDataSource: CodeNetworkDataSource,
    private val cacheDataSource: CodeCacheDataSource,
    private val networkManager: NetworkManager,
    private val mapper: DataToDomainModelMapper
) : CodeRepository {
    override suspend fun storeCopiedCode(code: String): Either<Failure, String> {
        return Either.Success(cacheDataSource.storeCopiedCode(code))
    }

    override suspend fun getAllCodes(): Either<Failure, DomainCodeModel> {
        if (!networkManager.isNetworkAvailable()) {
            return Either.Error(Failure.NetworkFailure.NetworkConnection)
        }

        return when (val result = networkDataSource.getAllCodes()) {
            is Either.Error -> result
            is Either.Success -> {
                try {
                    val mappedData = mapper.map(result.data)
                    cacheDataSource.updateCodes(mappedData)
                    Either.Success(mappedData)
                } catch (exception: Exception) {
                    Either.Error(Failure.MappingFailure)
                }
            }
        }
    }

    override suspend fun getAllFilteredCodes(filter: Map<String, String>): Either<Failure, DomainCodeModel> {
        return Either.Success(cacheDataSource.getAllCodesFilteredByCity(filter))
    }
}