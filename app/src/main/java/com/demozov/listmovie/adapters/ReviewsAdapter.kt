package com.demozov.listmovie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demozov.listmovie.databinding.ItemReviewBinding
import com.demozov.listmovie.pojo.Reviews

class ReviewsAdapter : ListAdapter<Reviews, ReviewsAdapter.ReviewsHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsHolder {
        return ReviewsHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewsHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ReviewsHolder(private var binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Reviews) {
                binding.apply {
                    authorName.text = item.author
                    createDate.text = item.created_at
                    review.text = item.content
                }
            }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Reviews>() {
            override fun areItemsTheSame(oldItem: Reviews, newItem: Reviews): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Reviews, newItem: Reviews): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }


}