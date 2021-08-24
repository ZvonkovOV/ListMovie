package com.demozov.listmovie.fragments

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.demozov.listmovie.R
import com.demozov.listmovie.adapters.MainMovieAdapter
import com.demozov.listmovie.adapters.OnReachEndListener
import com.demozov.listmovie.databinding.FragmentDetailMovieBinding
import com.demozov.listmovie.databinding.FragmentMainMovieBinding
import com.demozov.listmovie.models.MainMovieViewModel
import com.demozov.listmovie.pojo.Movie

class MainMovieFragment : Fragment() {

    private val viewModel: MainMovieViewModel by viewModels()

    private var _binding: FragmentMainMovieBinding? = null
    private val binding get() = _binding!!

    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentMainMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainMovieAdapter { movie ->
            val action = MainMovieFragmentDirections
                .actionMainMovieFragmentToDetailMovieFragment(
                    movieId = movie.id.toString(),
                    title = movie.title
                )
            this.findNavController().navigate(action)
        }
        binding.recyclerViewMain.adapter = adapter
        viewModel.movies.observe(this.viewLifecycleOwner) { listMovie ->
            if (adapter.currentList.isNullOrEmpty()) {
                adapter.submitList(listMovie.results)
            } else {
                val tmp = adapter.currentList.toMutableList()
                tmp.addAll(listMovie.results)
                adapter.submitList(tmp)
            }
        }
        binding.recyclerViewMain.layoutManager = GridLayoutManager(this.context, getSpanCount())
        viewModel.getMovies(page)
        adapter.setOnReachEndListener(object : OnReachEndListener {
            override fun onReachEnd() {
                page++
                viewModel.getMovies(page)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_favourite -> {
                val actions = MainMovieFragmentDirections.actionMainMovieFragmentToFavouriteMovieFragment()
                this.findNavController().navigate(actions)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getSpanCount(): Int {
        val dMetrics = Resources.getSystem().displayMetrics
        return ((dMetrics.widthPixels / dMetrics.density) / 150).toInt()
    }
}