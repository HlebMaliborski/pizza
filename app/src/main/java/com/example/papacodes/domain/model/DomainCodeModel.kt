package com.example.papacodes.domain.model


data class DomainCodeModel(
    val codes: List<DomainCode> = emptyList(),
    val maxPrice: Int = 0,
    val minPrice: Int = 0
)