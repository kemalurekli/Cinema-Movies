package com.kemalurekli.cinema.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.kemalurekli.cinema.databinding.FragmentHomeBinding
import com.kemalurekli.cinema.model.MovieResult
import com.kemalurekli.cinema.util.Status
import com.kemalurekli.cinema.view.adapter.MovieRecyclerAdapter
import com.kemalurekli.cinema.viewmodel.HomeFragmentViewModel
import javax.inject.Inject

class HomeFragment @Inject constructor(
    val movieRecyclerAdapter: MovieRecyclerAdapter
) : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : HomeFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeFragmentViewModel::class.java]
        binding.rvMovies.adapter = movieRecyclerAdapter
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSearch.setOnClickListener {
            val userInputMovieName = binding.sbMovie.text.toString()
            viewModel.searchForMovies(userInputMovieName)
            subscribeToObservers()
            hideKeyboard()

        }
    }
    private fun subscribeToObservers() {
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val movieLists =  it.data
                    if (movieLists != null && movieLists.Response.toBoolean()) {
                        movieRecyclerAdapter.movies = listOf(it.data)
                        binding.tvFoundMessage.visibility = View.GONE
                        binding.rvMovies.visibility = View.VISIBLE
                    }else
                    {
                        binding.rvMovies.visibility = View.GONE
                        binding.tvFoundMessage.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}