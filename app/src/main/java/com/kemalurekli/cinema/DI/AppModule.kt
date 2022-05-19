package com.kemalurekli.cinema.DI

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kemalurekli.cinema.R
import com.kemalurekli.cinema.api.RetrofitAPI
import com.kemalurekli.cinema.repo.MovieRepository
import com.kemalurekli.cinema.repo.MovieRepositoryInterface
import com.kemalurekli.cinema.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun injectGlide (@ApplicationContext context: Context) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.local_movies_24).error(R.drawable.local_movies_24)
    )

    @Singleton
    @Provides
    fun injectNormalRepo(api: RetrofitAPI) = MovieRepository(api) as MovieRepositoryInterface

}