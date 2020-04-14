package com.example.papacodes.data.mapper

import com.example.papacodes.common.mapper.Mapper
import com.example.papacodes.data.model.DataCodeModelDto
import com.example.papacodes.domain.model.DomainCode
import com.example.papacodes.domain.model.DomainCodeModel
import kotlin.math.roundToInt

class DataToDomainModelMapperImpl : DataToDomainModelMapper {
    override fun map(data: DataCodeModelDto): DomainCodeModel {
        val priceFromRegex = "[0-9]*\\.?[0-9]+".toRegex()
        val codes: List<DomainCode> = data.codes.map {
            val listItems = it.name.split("-").toList().reversed()
            DomainCode(
                it.code,
                listItems[2].trimStart(),
                listItems[1].replace(".", ",").trimStart(),
                listItems[0].trimStart(),
                priceFromRegex.find(listItems[1])?.value?.toDouble()?.roundToInt() ?: 0
            )
        }

        val maxPrice = codes.maxBy { it.priceFrom }?.priceFrom ?: 0
        val minPrice = codes.minBy { it.priceFrom }?.priceFrom ?: 0

        return DomainCodeModel(codes, maxPrice, minPrice)
    }
}

interface DataToDomainModelMapper : Mapper<DataCodeModelDto, DomainCodeModel>