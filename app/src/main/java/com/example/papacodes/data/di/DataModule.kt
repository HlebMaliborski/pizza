package com.example.papacodes.data.di

import com.example.papacodes.data.api.ApiPaths
import com.example.papacodes.data.api.ApiService
import com.example.papacodes.data.cache.CodeCache
import com.example.papacodes.data.cache.CodeCacheImpl
import com.example.papacodes.data.datasource.CodeCacheDataSource
import com.example.papacodes.data.datasource.CodeCacheDataSourceImpl
import com.example.papacodes.data.datasource.CodeNetworkDataSource
import com.example.papacodes.data.datasource.CodeNetworkDataSourceImpl
import com.example.papacodes.data.mapper.DataToDomainModelMapper
import com.example.papacodes.data.mapper.DataToDomainModelMapperImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single<DataToDomainModelMapper> { DataToDomainModelMapperImpl() }
    single<CodeNetworkDataSource> { CodeNetworkDataSourceImpl(api = get()) }
    single<CodeCacheDataSource> { CodeCacheDataSourceImpl(cache = get()) }
    single<CodeCache> { CodeCacheImpl(context = get()) }

    single { provideOkHttpClient(get()) }
    single { provideHttpLoggingInterceptor() }
    single { provideRetrofit(get()) }
    single { provideApiInterface(get()) }
}

fun provideApiInterface(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(ApiPaths.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .writeTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .connectTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level =
        HttpLoggingInterceptor.Level.NONE
    return httpLoggingInterceptor
}
