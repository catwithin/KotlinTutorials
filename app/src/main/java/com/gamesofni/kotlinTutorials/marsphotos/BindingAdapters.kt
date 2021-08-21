package com.gamesofni.kotlinTutorials.marsphotos

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gamesofni.kotlinTutorials.R
import com.gamesofni.kotlinTutorials.marsphotos.network.MarsPhoto
import com.gamesofni.kotlinTutorials.marsphotos.overview.MarsApiStatus
import com.gamesofni.kotlinTutorials.marsphotos.overview.PhotoGridAdapter


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        // Load the image in the background using Coil.
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri){
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }

    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<MarsPhoto>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)

}


// setting up a custom view to show the status of fetch
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: MarsApiStatus?) {
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }

}
