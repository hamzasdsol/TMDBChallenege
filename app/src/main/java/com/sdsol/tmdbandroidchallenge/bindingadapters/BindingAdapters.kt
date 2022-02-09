package com.sdsol.tmdbandroidchallenge.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sdsol.tmdbandroidchallenge.R
import getReleaseYear
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter

import com.bumptech.glide.request.RequestOptions




@BindingAdapter("android:srcImage")
fun setImageSrc(imageView: ImageView, srcImage: String?) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transform(FitCenter(), RoundedCorners(8))
    Glide.with(imageView.context).load(getFullURL(srcImage)).apply(requestOptions).into(imageView)
}

fun getFullURL(srcImage: String?) : String{
    return "https://image.tmdb.org/t/p/w500$srcImage"
}

@BindingAdapter("android:releaseYear")
fun setYear(textView: TextView, year: String?) {
    textView.text = year!!.getReleaseYear()
}