package dev.shreyansh.androiddevsyt.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.shreyansh.androiddevsyt.databinding.VideoListItemBinding
import dev.shreyansh.androiddevsyt.domain.Video

class AndroidDevsRecyclerAdapter (val clickListener : ClickListener) : ListAdapter<Video, AndroidDevsRecyclerAdapter.ViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidDevsRecyclerAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AndroidDevsRecyclerAdapter.ViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model,clickListener)
    }

    class ViewHolder private constructor(val binding: VideoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Video,clickListener : ClickListener) {
            binding.video = model
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VideoListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class DiffUtilCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }

}

class ClickListener(val clickListener: (model : Video) -> Unit){
    fun onClick(model : Video) = clickListener(model)
}