package com.example.papacodes.presentation.mapper

import com.example.papacodes.common.mapper.Mapper
import com.example.papacodes.domain.model.DomainCodeModel
import com.example.papacodes.presentation.model.PresentationCode
import com.example.papacodes.presentation.model.PresentationCodeModel

class DomainToPresentationMapperImpl : DomainToPresentationMapper {
    override fun map(data: DomainCodeModel): PresentationCodeModel {
        val codes: List<PresentationCode> = data.codes.map {
            PresentationCode(it.code, it.stock, it.price, it.city, it.priceFrom)
        }

        return PresentationCodeModel(codes, data.maxPrice, data.minPrice)
    }
}

interface DomainToPresentationMapper : Mapper<DomainCodeModel, PresentationCodeModel>