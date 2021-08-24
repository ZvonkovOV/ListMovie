package com.demozov.listmovie.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.demozov.listmovie.MovieApplication
import com.demozov.listmovie.adapters.FavouriteMovieAdapter
import com.demozov.listmovie.adapters.OnReachEndListener
import com.demozov.listmovie.databinding.FragmentFavouriteMovieBinding
import com.demozov.listmovie.models.FavouriteMovieViewModel
import com.demozov.listmovie.models.FavouriteMovieViewModel.FavouriteMovieViewModelFactory
import com.demozov.listmovie.pojo.FavouriteMovie


class FavouriteMovieFragment : Fragment() {

    private val viewModel: FavouriteMovieViewModel by viewModels {
        FavouriteMovieViewModelFactory(
            (activity?.application as MovieApplication).database.movieDao()
        )
    }

    private var _binding: FragmentFavouriteMovieBinding? = null
    private val binding get() = _binding!!
    private var listIdMovie = emptyList<FavouriteMovie>()
    private var numMovie = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavouriteMovieAdapter {movie ->
            val action = FavouriteMovieFragmentDirections
                .actionFavouriteMovieFragmentToDetailMovieFragment(
                    movieId = movie.id.toString(),
                    title = movie.title
                )
            this.findNavController().navigate(action)
        }
        binding.recyclerViewFavourite.adapter = adapter
        binding.recyclerViewFavourite.layoutManager = GridLayoutManager(this.context, getSpanCount())
        viewModel.allMovies.observe(this.viewLifecycleOwner) { listFavourite ->
            if (!listFavourite.isNullOrEmpty()) {
                listIdMovie = listFavourite
                download(listIdMovie[numMovie].id)
            }
        }
        viewModel.getAllMovies()
        viewModel.itemMovie.observe(this.viewLifecycleOwner) {
            adapter.addOne(it)
        }
        adapter.setOnReachEndListener(object : OnReachEndListener {
            override fun onReachEnd() {
                if (numMovie < listIdMovie.size) {
                    download(listIdMovie[numMovie].id)
                    Log.d("game", "${listIdMovie.size}")
                }
            }
        })
    }

    private fun download(id: Int) {
        viewModel.getMovie(id)
        numMovie++
    }

    private fun getSpanCount(): Int {
        val dMetrics = Resources.getSystem().displayMetrics
        return ((dMetrics.widthPixels / dMetrics.density) / 150).toInt()
    }
}