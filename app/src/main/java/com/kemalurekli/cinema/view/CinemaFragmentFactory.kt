package com.kemalurekli.cinema.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.kemalurekli.cinema.view.adapter.MovieRecyclerAdapter
import javax.inject.Inject

class CinemaFragmentFactory @Inject constructor(
    private val glide : RequestManager,
    private val movieRecyclerAdapter: MovieRecyclerAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            HomeFragment::class.java.name -> HomeFragment(movieRecyclerAdapter)
            DetailFragment::class.java.name -> DetailFragment(glide)
            else -> super.instantiate(classLoader, className)
        }

    }
}