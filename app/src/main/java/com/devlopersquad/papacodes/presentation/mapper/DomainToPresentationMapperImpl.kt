package com.devlopersquad.papacodes.presentation.mapper

import com.devlopersquad.papacodes.common.mapper.Mapper
import com.devlopersquad.papacodes.domain.model.DomainCodeModel
import com.devlopersquad.papacodes.presentation.model.PresentationCode
import com.devlopersquad.papacodes.presentation.model.PresentationCodeModel

class DomainToPresentationMapperImpl : DomainToPresentationMapper {
    override fun map(data: DomainCodeModel): PresentationCodeModel {
        val codes: List<PresentationCode> = data.codes.map {
            PresentationCode(it.code, it.stock, it.price, it.city, it.priceFrom)
        }

        return PresentationCodeModel(codes, data.maxPrice, data.minPrice)
    }
}

interface DomainToPresentationMapper : Mapper<DomainCodeModel, PresentationCodeModel>