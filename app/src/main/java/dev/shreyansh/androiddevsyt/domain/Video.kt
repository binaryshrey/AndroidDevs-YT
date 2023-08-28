package dev.shreyansh.androiddevsyt.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Video(
    val url: String,
    val title: String,
    val description: String,
    val updated: String,
    val thumbnail: String
) : Parcelable