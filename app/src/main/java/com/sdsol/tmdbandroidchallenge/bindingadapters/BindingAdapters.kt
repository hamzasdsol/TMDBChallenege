package com.sdsol.tmdbandroidchallenge.bindingadapters

import android.graphics.Color
import android.graphics.Typeface
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
import java.text.DecimalFormat
import java.util.*


@BindingAdapter("android:srcImage")
fun setImageSrc(imageView: ImageView, srcImage: String?) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transform(FitCenter(), RoundedCorners(8))
    Glide.with(imageView.context).load(getFullURL(srcImage)).apply(requestOptions).into(imageView)
}

@BindingAdapter("android:srcBackdrop")
fun setImageSrcWithoutCornerRadius(imageView: ImageView, srcImage: String?) {
    Glide.with(imageView.context).load(getFullURL(srcImage)).into(imageView)
}

private fun getFullURL(srcImage: String?): String {
    return "https://image.tmdb.org/t/p/w500$srcImage"
}

@BindingAdapter("android:releaseYear")
fun setYear(textView: TextView, year: String?) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
    if (currentYear == (year ?: "").getReleaseYear()) {
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.setTextColor(Color.parseColor("#FF0000"));
        textView.text = currentYear
    } else {
        textView.setTypeface(textView.typeface, Typeface.NORMAL)
        textView.setTextColor(Color.parseColor("#000000"));
        textView.text = (year ?: "").getReleaseYear()
    }
}