package com.android.movieoftheday.app.retrofit

import androidx.annotation.NonNull
import com.android.movieoftheday.BuildConfig
import com.android.movieoftheday.data.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {

        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY = "5d190a4676660309ee5187b997f90f2c"

        private const val TIMEOUT_SECONDS = 60L
        private const val LOGGING_INTERCEPTOR = "loggingInterceptor"
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideHttpClient(
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Named(LOGGING_INTERCEPTOR)
    fun provideLoggingInterceptor(): Interceptor = ApiLoggingInterceptor()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}

class ApiLoggingInterceptor(
    private val origin: HttpLoggingInterceptor = HttpLoggingInterceptor()
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            origin.level = HttpLoggingInterceptor.Level.BODY
        } else {
            origin.level = HttpLoggingInterceptor.Level.NONE
        }
        return origin.intercept(chain)
    }
}