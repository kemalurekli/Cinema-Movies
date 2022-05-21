package com.kemalurekli.cinema.repo

import com.kemalurekli.cinema.model.MovieResult
import com.kemalurekli.cinema.util.Resource

interface MovieRepositoryInterface {
    suspend fun searchMovie (movieString : String) : Resource<MovieResult>
    suspend fun searchMovieWithImdbId (movieString : String) : Resource<MovieResult>
}