package com.example.papacodes.data.di

import com.example.papacodes.data.api.ApiPaths
import com.example.papacodes.data.api.ApiService
import com.example.papacodes.data.cache.CodeCache
import com.example.papacodes.data.cache.CodeCacheImpl
import com.example.papacodes.data.datasource.*
import com.example.papacodes.data.mapper.DataToDomainModelMapper
import com.example.papacodes.data.mapper.DataToDomainModelMapperImpl
import com.example.papacodes.data.repository.SharedRepositoryImpl
import com.example.papacodes.data.shared.Shared
import com.example.papacodes.data.shared.SharedImpl
import com.example.papacodes.domain.repository.SharedRepository
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
    single<Shared> { SharedImpl(sharedPreferences = get()) }
    single<SharedDataSource> { SharedDataSourceImpl(shared = get()) }
    single<SharedRepository> { SharedRepositoryImpl(sharedDataSource = get()) }

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
