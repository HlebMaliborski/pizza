package com.example.papacodes.common.mapper

interface Mapper<in T, out K> {
    fun map(data: T): K
}

interface MapperForTwoData<in T, in O, out K> {
    fun map(data1: T, data2: O): K
}