package com.demozov.listmovie.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.demozov.listmovie.MovieApplication
import com.demozov.listmovie.R
import com.demozov.listmovie.adapters.ReviewsAdapter
import com.demozov.listmovie.databinding.FragmentDetailMovieBinding
import com.demozov.listmovie.models.DetailMovieViewModel
import com.demozov.listmovie.models.DetailMovieViewModelFactory
import com.demozov.listmovie.network.BASE_PICTURE_URL
import com.demozov.listmovie.network.PICTURE_SIZE_500


class DetailMovieFragment : Fragment() {

    private val viewModel: DetailMovieViewModel by viewModels {
        DetailMovieViewModelFactory(
            (activity?.application as MovieApplication).database.movieDao()
        )
    }

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: DetailMovieFragmentArgs by navArgs()
    private var isFavourite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.itemMovie.observe(this.viewLifecycleOwner) {itemMovie ->
            binding.apply {
                val imgUrl = "$BASE_PICTURE_URL$PICTURE_SIZE_500${itemMovie.posterPath}"
                poster.load(imgUrl) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.broken_image)
                }
                nameMovie.text = itemMovie.title
                voteAverage.text = itemMovie.voteAverage.toString()
                releaseDate.text = itemMovie.releaseDate
                description.text = itemMovie.overview
            }
        }
        val adapter = ReviewsAdapter()
        val id = navigationArgs.movieId.toInt()

        binding.recyclerViewDetail.adapter = adapter
        binding.recyclerViewDetail.layoutManager = LinearLayoutManager(this.context)
        viewModel.listReviews.observe(this.viewLifecycleOwner) {listReviews ->
            adapter.submitList(listReviews)
        }
        viewModel.allMovies.observe(this.viewLifecycleOwner) {listMovies ->
            if (!listMovies.isNullOrEmpty()) {
                isFavourite = listMovies.map { it.id }.contains(id)
            }
        }
        isFavourite
        binding.floatingButton.setOnClickListener {
            if (isFavourite) {
                viewModel.deleteMovie(id)
            } else {
                viewModel.addMovie(id)
            }
        }

        viewModel.apply {
            getMovie(id)
            getReviews(id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_favourite -> {
                val actions = DetailMovieFragmentDirections.actionDetailMovieFragmentToFavouriteMovieFragment()
                this.findNavController().navigate(actions)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}