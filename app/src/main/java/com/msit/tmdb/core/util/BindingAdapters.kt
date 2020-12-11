package com.msit.tmdb.core.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("url", "isCircular", "placeholder", "error", requireAll = false)
fun ImageView.bindUrl(
    url: String,
    isCircular: Boolean = false,
    placeholder: Drawable?,
    error: Drawable?,
) {
    val request = Glide.with(this).load(url)
    if (isCircular) request.circleCrop()
    if (placeholder != null) request.placeholder(placeholder)
    if (error != null) request.error(error)
    request.transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

}