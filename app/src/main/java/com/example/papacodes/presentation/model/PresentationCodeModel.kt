package com.example.papacodes.presentation.model

data class PresentationCodeModel(
    val codes: List<PresentationCode>,
    val maxPrice: Int = 0,
    val minPrice: Int = 0
)