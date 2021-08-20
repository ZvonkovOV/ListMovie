package com.demozov.listmovie.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.demozov.listmovie.R
import com.demozov.listmovie.databinding.ItemMovieBinding
import com.demozov.listmovie.network.BASE_PICTURE_URL
import com.demozov.listmovie.network.PICTURE_SIZE_185
import com.demozov.listmovie.pojo.DetailMovie
class FavouriteMovieAdapter(private val onItemClicked: (DetailMovie) -> Unit) :
    RecyclerView.Adapter<FavouriteMovieAdapter.FavouriteMovieHolder>() {

    private var onReachEndListener: OnReachEndListener? = null
    private var listMovie: List<DetailMovie> = ArrayList()

    class FavouriteMovieHolder(private var binding: ItemMovieBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: DetailMovie) {
                    binding.apply {
                        val imgUrl = "$BASE_PICTURE_URL$PICTURE_SIZE_185${item.posterPath}"
                        imageViewItemMovie.load(imgUrl) {
                            placeholder(R.drawable.loading_animation)
                            error(R.drawable.broken_image)
                        }
                        voteItem.text = item.voteAverage.toString()
                    }
                }
            }

    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        this.onReachEndListener = onReachEndListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMovieHolder {
        return FavouriteMovieHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: FavouriteMovieHolder, position: Int) {
        if (position > listMovie.size - 4 && position < listMovie.size) {
            onReachEndListener?.onReachEnd()
        }
        val current = listMovie[position]
        holder.itemView.setOnClickListener { onItemClicked(current) }
        holder.bind(current)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addOne(item: DetailMovie) {
        (listMovie as ArrayList).add(item)
        notifyDataSetChanged()
    }
}