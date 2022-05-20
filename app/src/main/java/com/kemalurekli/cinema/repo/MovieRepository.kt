package com.kemalurekli.cinema.repo

import com.kemalurekli.cinema.api.RetrofitAPI
import com.kemalurekli.cinema.model.MovieResult
import com.kemalurekli.cinema.util.Resource
import java.lang.Exception
import javax.inject.Inject

class MovieRepository @Inject constructor(
private val retrofitAPI: RetrofitAPI
) : MovieRepositoryInterface {
    override suspend fun searchMovie(movieString: String): Resource<MovieResult> {
        return try {
            val response = retrofitAPI.searchMovie(movieString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : Exception){
            Resource.error("Check Your Connection!", null)
        }
    }

    override suspend fun searchMovieWithImdbId(movieString: String): Resource<MovieResult> {
        return try {
            val response = retrofitAPI.searchMovieWithImdbId(movieString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : Exception){
            Resource.error("Check Your Connection!", null)
        }
    }
}