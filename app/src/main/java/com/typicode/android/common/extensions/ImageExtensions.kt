package com.typicode.android.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String?, roundedCorners: Boolean = true) {
    url?.let {
        Glide.with(this)
            .load(it)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(
                RequestOptions().transform(
                    MultiTransformation(
                        listOfNotNull(
                            CenterCrop(),
                            if (roundedCorners) RoundedCorners(2.dp) else null
                        )
                    )
                )
            )
            .into(this)

    }
}
