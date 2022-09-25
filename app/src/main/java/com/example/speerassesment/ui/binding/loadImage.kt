package com.example.speerassesment.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.speerassesment.R

@BindingAdapter("app:loadImage")
fun ImageView.loadImage(url: String) {
    this.post {
        val myOptions = RequestOptions()
            .override(this.width, this.height)
            .fitCenter()
        Glide
            .with(context)
            .load(url)
            .placeholder(R.drawable.default_img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(myOptions)
            .into(this)
    }
}
