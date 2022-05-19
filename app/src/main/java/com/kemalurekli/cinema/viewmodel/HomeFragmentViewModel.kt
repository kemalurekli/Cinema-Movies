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
class HomeFragmentViewModel @Inject constructor(
    private val repository: MovieRepositoryInterface
)
    :ViewModel() {
    private val movies = MutableLiveData<Resource<MovieResult>>()
    val movieList : LiveData<Resource<MovieResult>>
    get() = movies

    fun searchForMovies (movieName : String){
        if (movieName.isEmpty()){
            return
        }
        movies.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchMovie(movieName)
            movies.value = response
        }

    }
}