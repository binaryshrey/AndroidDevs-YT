package dev.shreyansh.androiddevsyt.utils

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.shreyansh.androiddevsyt.viewmodel.AndroidDevsViewModel

@BindingAdapter("imageUrl")
fun bind(image: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(image.context)
            .load(imgUri)
            .into(image)
    }
}

@BindingAdapter("title")
fun setTitle(videoTitle: TextView, title: String?) {
    title?.let {
        videoTitle.text = it
    }
}

@BindingAdapter("description")
fun setDescription(videoDescription: TextView, description: String?) {
    description?.let {
        videoDescription.text = it
    }
}

@BindingAdapter("progressStatus")
fun setStatus(linearLayout: LinearLayout, status : AndroidDevsViewModel.APIStatus?){
    when(status){
        AndroidDevsViewModel.APIStatus.LOADING -> linearLayout.visibility = View.VISIBLE
        AndroidDevsViewModel.APIStatus.DONE -> linearLayout.visibility = View.INVISIBLE
        AndroidDevsViewModel.APIStatus.FAILURE -> linearLayout.visibility = View.INVISIBLE
        else -> linearLayout.visibility = View.INVISIBLE
    }
}

//@BindingAdapter("errorStatus")
//fun errorStatus(errorTextView: TextView, status: AndroidDevsViewModel.APIStatus?) {
//    when (status) {
//        AndroidDevsViewModel.APIStatus.LOADING -> errorTextView.visibility = View.INVISIBLE
//        AndroidDevsViewModel.APIStatus.DONE -> errorTextView.visibility = View.INVISIBLE
//        AndroidDevsViewModel.APIStatus.FAILURE -> errorTextView.visibility = View.VISIBLE
//    }
//}

//@BindingAdapter("recyclerViewStatus")
//fun recyclerViewStatus(recyclerView: RecyclerView, status: AndroidDevsViewModel.APIStatus?) {
//    when (status) {
//        AndroidDevsViewModel.APIStatus.LOADING -> recyclerView.visibility = View.INVISIBLE
//        AndroidDevsViewModel.APIStatus.DONE -> recyclerView.visibility = View.VISIBLE
//        AndroidDevsViewModel.APIStatus.FAILURE -> recyclerView.visibility = View.INVISIBLE
//    }
//}