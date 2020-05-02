package com.devlopersquad.papacodes.data.datasource

import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.data.api.ApiService
import com.devlopersquad.papacodes.data.model.DataCodeModelDto

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