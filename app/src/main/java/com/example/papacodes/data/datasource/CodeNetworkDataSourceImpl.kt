package com.example.papacodes.data.datasource

import com.example.papacodes.common.response.Either
import com.example.papacodes.common.response.Failure
import com.example.papacodes.data.api.ApiService
import com.example.papacodes.data.model.DataCodeModelDto

class CodeNetworkDataSourceImpl(private val api: ApiService) : CodeNetworkDataSource,
    BaseDataSource() {
    override suspend fun getAllCodes(): Either<Failure, DataCodeModelDto> =
        runRequestTryCatch {
            runRequest(api.getAllCodes())
        }
}

interface CodeNetworkDataSource {
    suspend fun getAllCodes(): Either<Failure, DataCodeModelDto>
}