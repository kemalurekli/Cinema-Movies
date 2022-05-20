package com.kemalurekli.cinema.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalurekli.cinema.model.MovieResult
import com.kemalurekli.cinema.repo.MovieRepositoryInterface
import com.kemalurekli.cinema.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(
    private val repository: MovieRepositoryInterface
) :ViewModel() {

    private val movie = MutableLiveData<Resource<MovieResult>>()
    val movieList : LiveData<Resource<MovieResult>>
        get() = movie

    fun searchWithImdbId (movieImdbId : String){
        if (movieImdbId.isEmpty()){
            return
        }
        movie.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchMovieWithImdbId(movieImdbId)
            movie.value = response
        }

    }

}