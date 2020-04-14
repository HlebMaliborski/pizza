package com.example.papacodes.data.repository

import com.example.core_common.result.Failure
import com.example.papacodes.common.network.NetworkManager
import com.example.papacodes.common.response.Either
import com.example.papacodes.data.datasource.CodeCacheDataSource
import com.example.papacodes.data.datasource.CodeNetworkDataSource
import com.example.papacodes.data.mapper.DataToDomainModelMapper
import com.example.papacodes.domain.model.DomainCodeModel
import com.example.papacodes.domain.repository.CodeRepository

class CodeRepositoryImpl(
    private val networkDataSource: CodeNetworkDataSource,
    private val cacheDataSource: CodeCacheDataSource,
    private val networkManager: NetworkManager,
    private val mapper: DataToDomainModelMapper
) : CodeRepository {
    override suspend fun getAllCodes(): Either<Failure, DomainCodeModel> {
        if (!networkManager.isNetworkAvailable()) {
            return Either.Error(Failure.NetworkFailure.NetworkConnection)
        }

        return when (val result = networkDataSource.getAllCodes()) {
            is Either.Error -> result
            is Either.Success -> {
                val mappedData = mapper.map(result.data)
                cacheDataSource.updateCodes(mappedData)
                Either.Success(mappedData)
            }
        }
    }

    override suspend fun getAllFilteredCodes(filter: Map<String, String>): Either<Failure, DomainCodeModel> {
        return Either.Success(cacheDataSource.getAllCodesFilteredByCity(filter))
    }
}