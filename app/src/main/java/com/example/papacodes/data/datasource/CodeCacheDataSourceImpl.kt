package com.example.papacodes.data.datasource

import com.example.papacodes.data.cache.CodeCache
import com.example.papacodes.domain.model.DomainCodeModel
import com.example.papacodes.presentation.viewmodel.CodeViewModel.Companion.CITY
import com.example.papacodes.presentation.viewmodel.CodeViewModel.Companion.PRICE
import com.example.papacodes.presentation.viewmodel.CodeViewModel.Companion.RESET
import com.example.papacodes.presentation.viewmodel.CodeViewModel.Companion.SIZE

class CodeCacheDataSourceImpl(private val cache: CodeCache) : CodeCacheDataSource,
    BaseDataSource() {
    override suspend fun getAllCodes(): DomainCodeModel {
        return cache.get()
    }

    override suspend fun getAllCodesFilteredByCity(filter: Map<String, String>): DomainCodeModel {
        val cityFilter = filter[CITY] ?: RESET
        val sizeFilter = filter[SIZE] ?: RESET
        val priceFilter = filter[PRICE] ?: RESET

        val filteredDataByCity = if (cityFilter != RESET) cache.get().codes.filter {
            it.city.contains(cityFilter) || it.city.contains("Все города")
        } else cache.get().codes

        val filteredDataBySize = if (sizeFilter != RESET) filteredDataByCity.filter {
            it.stock.contains(sizeFilter)
        } else filteredDataByCity

        val filteredDataByPrice = if (priceFilter != RESET) {
            filteredDataBySize.filter {
                it.priceFrom <= priceFilter.toInt()
            }
        } else filteredDataBySize

        return DomainCodeModel(filteredDataByPrice)
    }

    override suspend fun updateCodes(dataCode: DomainCodeModel) {
        cache.put(dataCode)
    }

    override suspend fun isCached(): Boolean = cache.isCached()
}

interface CodeCacheDataSource {
    suspend fun getAllCodes(): DomainCodeModel
    suspend fun getAllCodesFilteredByCity(filter: Map<String, String>): DomainCodeModel
    suspend fun updateCodes(dataCode: DomainCodeModel)
    suspend fun isCached(): Boolean
}