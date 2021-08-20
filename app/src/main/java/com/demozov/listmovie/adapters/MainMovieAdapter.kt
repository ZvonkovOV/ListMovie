package com.demozov.listmovie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.demozov.listmovie.R
import com.demozov.listmovie.databinding.ItemMovieBinding
import com.demozov.listmovie.network.BASE_PICTURE_URL
import com.demozov.listmovie.network.PICTURE_SIZE_185
import com.demozov.listmovie.pojo.Movie

class MainMovieAdapter(private val onItemClicked: (Movie) -> Unit) :
    ListAdapter<Movie, MainMovieAdapter.ItemMovieHolder>(DiffCallback) {

    private var onReachEndListener: OnReachEndListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieHolder {
        return ItemMovieHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemMovieHolder, position: Int) {
        if (currentList.size >= 20 && position > currentList.size - 4) {
            onReachEndListener?.onReachEnd()
        }
        val current = getItem(position)
        holder.itemView.setOnClickListener { onItemClicked(current) }
        holder.bind(current)
    }

    class ItemMovieHolder(private var binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Movie) {
                binding.apply {
                    val imgUrl = "${BASE_PICTURE_URL}${PICTURE_SIZE_185}${item.poster_path}"
                    imageViewItemMovie.load(imgUrl) {
                        placeholder(R.drawable.loading_animation)
                        error(R.drawable.broken_image)
                    }
                    voteItem.text = item.vote_average.toString()
                }
            }
        }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        this.onReachEndListener = onReachEndListener
    }
}