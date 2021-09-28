package com.gamesofni.kotlinTutorials.marsphotos.network

import com.squareup.moshi.Json

data class MarsProperty (
    // fields' names and types correspond to the key names and value types in JSON
    val id: String,
    // for JSON key names that can't be directly translated:
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double,
) {
    val isRental
        get() = type == "rent"
}

