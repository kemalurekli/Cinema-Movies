package com.kemalurekli.cinema.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.kemalurekli.cinema.databinding.FragmentDetailBinding
import com.kemalurekli.cinema.util.Status
import com.kemalurekli.cinema.viewmodel.DetailFragmentViewModel
import com.kemalurekli.cinema.viewmodel.HomeFragmentViewModel
import javax.inject.Inject


class DetailFragment @Inject constructor(
    private val glide : RequestManager
): Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : DetailFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[DetailFragmentViewModel::class.java]
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imdbID: String = arguments?.get("imdbID").toString()
        viewModel.searchWithImdbId(imdbID)
        getMovieWithId()


    }

    private fun getMovieWithId() {
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBarDetailPage.visibility = View.GONE
                    val movie =  it.data
                    if (movie != null && movie.Response.toBoolean()) {
                        binding.tvDetailErrorMessage.visibility = View.GONE
                        glide.load(movie.Poster).into(binding.ivDetailMovie)
                        binding.tvDetailMovieName.text = movie.Title
                        binding.tvDetailMovieDetails.text = ("${movie.Title} released ${movie.Released}." +
                                " The movie's run time ${movie.Runtime}. ${movie.Genre} as genre. " +
                                "The most famous actors are ${movie.Actors} ${movie.Plot}")
                    }else
                    {
                        binding.apply {
                            tvDetailErrorMessage.visibility = View.VISIBLE
                            tvDetailMovieName.visibility = View.GONE
                            tvDetailMovieDetails.visibility = View.GONE
                            ivDetailMovie.visibility = View.GONE
                        }

                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                    binding.progressBarDetailPage.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBarDetailPage.visibility = View.VISIBLE
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}