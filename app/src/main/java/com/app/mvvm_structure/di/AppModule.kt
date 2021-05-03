package com.app.mvvm_structure.di

import android.content.Context
import com.app.mvvm_structure.data.remote.ApiService
import com.app.mvvm_structure.data.remote.AppDataSource
import com.app.mvvm_structure.data.remote.AppDataSourceImpl
import com.app.mvvm_structure.domain.repository.AppRepository
import com.app.mvvm_structure.domain.repository.AppRepositoryImpl
import com.app.mvvm_structure.utils.AppConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteAppDataSource

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        })

        return okHttpClient.build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @RemoteAppDataSource
    @Provides
    fun getAppDataSource(@ApplicationContext appContext: Context, apiService: ApiService, ioDispatcher: CoroutineDispatcher): AppDataSource {
        return AppDataSourceImpl(appContext, apiService, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideIODispatcher() = Dispatchers.IO



}

@Module
@InstallIn(ApplicationComponent::class)
object AppRepositoryModule {

    @Singleton
    @Provides
    fun getAppRepository(@ApplicationContext appContext: Context, @AppModule.RemoteAppDataSource remoteAppDataSource: AppDataSource): AppRepository {
        return AppRepositoryImpl(appContext, remoteAppDataSource)
    }

}