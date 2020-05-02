package com.devlopersquad.papacodes.data.di

import android.content.Context
import com.devlopersquad.papacodes.R
import com.devlopersquad.papacodes.data.api.ApiPaths
import com.devlopersquad.papacodes.data.api.ApiService
import com.devlopersquad.papacodes.data.cache.CodeCache
import com.devlopersquad.papacodes.data.cache.CodeCacheImpl
import com.devlopersquad.papacodes.data.datasource.*
import com.devlopersquad.papacodes.data.mapper.DataToDomainModelMapper
import com.devlopersquad.papacodes.data.mapper.DataToDomainModelMapperImpl
import com.devlopersquad.papacodes.data.repository.SharedRepositoryImpl
import com.devlopersquad.papacodes.data.shared.Shared
import com.devlopersquad.papacodes.data.shared.SharedImpl
import com.devlopersquad.papacodes.domain.repository.SharedRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


val dataModule = module {
    single<DataToDomainModelMapper> { DataToDomainModelMapperImpl() }
    single<CodeNetworkDataSource> { CodeNetworkDataSourceImpl(api = get()) }
    single<CodeCacheDataSource> { CodeCacheDataSourceImpl(cache = get()) }
    single<CodeCache> { CodeCacheImpl(context = get()) }
    single<Shared> { SharedImpl(sharedPreferences = get()) }
    single<SharedDataSource> { SharedDataSourceImpl(shared = get()) }
    single<SharedRepository> { SharedRepositoryImpl(sharedDataSource = get()) }

    single { provideOkHttpClient(get(), get()) }
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

fun provideOkHttpClient(
    context: Context,
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    // Loading CAs from an InputStream
    val cf = CertificateFactory.getInstance("X.509")
    var ca: Certificate? = null
    context.resources.openRawResource(R.raw.cert)
        .use { cert -> ca = cf.generateCertificate(cert) }

    // Creating a KeyStore containing our trusted CAs
    val keyStoreType: String = KeyStore.getDefaultType()
    val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
    keyStore.load(null, null)
    keyStore.setCertificateEntry("ca", ca)

    // Creating a TrustManager that trusts the CAs in our KeyStore.
    val trustManagerFactory =
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)
    val trustManagers = trustManagerFactory.trustManagers
    check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
        "Unexpected default trust managers:" + Arrays.toString(
            trustManagers
        )
    }

    val trustManager = trustManagers[0] as X509TrustManager

    // Creating an SSLSocketFactory that uses our TrustManager
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
    val builder = OkHttpClient.Builder()
    builder.sslSocketFactory(sslSocketFactory, trustManager)
    builder.writeTimeout(60L, TimeUnit.SECONDS)
    builder.readTimeout(60L, TimeUnit.SECONDS)
    builder.connectTimeout(60L, TimeUnit.SECONDS)
    builder.addInterceptor(httpLoggingInterceptor)
    builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
    builder.build()
    return builder.build()
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level =
        HttpLoggingInterceptor.Level.NONE
    return httpLoggingInterceptor
}
