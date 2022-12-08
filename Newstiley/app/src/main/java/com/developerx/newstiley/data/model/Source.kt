package com.developerx.newstiley.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable


data class Source(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
): Serializable