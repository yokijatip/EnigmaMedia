package com.enigma.enigmamedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.enigmamedia.data.remote.response.ListStoryItem
import com.enigma.enigmamedia.databinding.ItemMainBinding
import com.enigma.enigmamedia.utils.DateFormatter
import com.enigma.enigmamedia.utils.RandomAvatar

@Suppress("DEPRECATION")
class MainAdapterMVVM :
    ListAdapter<ListStoryItem, MainAdapterMVVM.StoryViewHolder>(StoryDiffCallback()) {

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

    inner class StoryViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storyItem: ListStoryItem) {

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(storyItem)
            }

            val randomAvatar = RandomAvatar().getRandomAvatar()

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

                val dateBeforeFormatter = storyItem.createdAt.toString()
                val dateAfterFormatter = DateFormatter().formatter(dateBeforeFormatter)
                tvDate.text = dateAfterFormatter
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val view = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder((view))
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class StoryDiffCallback : DiffUtil.ItemCallback<ListStoryItem>() {
        override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(storyItem: ListStoryItem)
    }


}