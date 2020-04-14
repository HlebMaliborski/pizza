package com.example.papacodes.data.api

import com.example.papacodes.data.model.DataCodeModelDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(ApiPaths.CODES)
    suspend fun getAllCodes(): Response<DataCodeModelDto>
}