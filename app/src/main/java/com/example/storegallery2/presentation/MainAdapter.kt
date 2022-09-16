package com.example.storegallery2.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storegallery2.R
import com.example.storegallery2.databinding.LayoutItemBinding


class MainAdapter : ListAdapter<ListItem, MainAdapter.MyViewHolder>(MainDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MyViewHolder(
        private val binding: LayoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListItem) {

            binding.titleText.text = item.title
            binding.priceText.text = item.price

            Glide.with(itemView.context)
                .load(item.cover)
                .into(binding.productImageImage)

            if (item.liked) {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_heart)
                    .into(binding.likedImage)
            } else {
                binding.likedImage.setImageResource(0)
            }
        }
    }

}
