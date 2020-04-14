package com.example.papacodes.data.model

import com.google.gson.annotations.SerializedName

data class DataCodeDto(
    val code: String,
    @SerializedName("image")
    val image: Boolean,
    @SerializedName("image_mobile_shop")
    val imageMobileShop: Boolean,
    @SerializedName("image_mobile_stock")
    val imageMobileStock: Boolean,
    val name: String
)