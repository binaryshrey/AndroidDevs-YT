package dev.shreyansh.androiddevsyt.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.shreyansh.androiddevsyt.databinding.VideoListItemBinding
import dev.shreyansh.androiddevsyt.domain.Video

class AndroidDevsRecyclerAdapter (val onClickListener: OnClickListener) : ListAdapter<Video, AndroidDevsRecyclerAdapter.ViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidDevsRecyclerAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AndroidDevsRecyclerAdapter.ViewHolder, position: Int) {
        val model = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(model)
        }
        holder.bind(model)
    }

    class ViewHolder private constructor(val binding: VideoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Video) {
            binding.video = model
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
    class OnClickListener(val clickListener: (model : Video) -> Unit){
        fun onClick(model : Video) = clickListener(model)
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

