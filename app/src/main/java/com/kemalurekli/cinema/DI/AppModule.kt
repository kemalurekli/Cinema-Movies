package com.kemalurekli.cinema.DI

import com.kemalurekli.cinema.api.RetrofitAPI
import com.kemalurekli.cinema.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitAPI () : RetrofitAPI {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
        baseUrl(BASE_URL).build().create(RetrofitAPI::class.java)
    }
}