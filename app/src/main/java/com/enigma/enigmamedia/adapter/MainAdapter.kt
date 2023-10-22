package com.enigma.enigmamedia.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.data.remote.response.ListStoryItem
import com.enigma.enigmamedia.databinding.ItemMainBinding
import com.enigma.enigmamedia.view.detail.DetailActivity

class MainAdapter : ListAdapter<ListStoryItem, MainAdapter.MainViewHolder>(StoryDiffCallback()) {

    private val list = ArrayList<ListStoryItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<ListStoryItem>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun getRandomAvatar(): Int {
            val avatarResourceDrawable = arrayOf(
                R.drawable.avatar1,
                R.drawable.avatar2,
                R.drawable.avatar3,
                R.drawable.avatar4
            )

            val randomIndex = (avatarResourceDrawable.indices).random()
            return avatarResourceDrawable[randomIndex]
        }

        fun bind(storyItem: ListStoryItem) {

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(storyItem)
            }

            val randomAvatar = getRandomAvatar()

            binding.apply {
                Glide.with(itemView)
                    .load(randomAvatar)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivAvatar)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(storyItem.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivPhoto)
                tvName.text = storyItem.name
            }
        }

    }

    class StoryDiffCallback : DiffUtil.ItemCallback<ListStoryItem>() {
        override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder((view))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {


        holder.bind(list[position])


    }

    //    Interface buat on item click listener
    interface OnItemClickCallback {
        fun onItemClicked(storyItem: ListStoryItem)
    }

}