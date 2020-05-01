package com.example.papacodes.common.mapper

interface Mapper<in T, out K> {
    fun map(data: T): K
}

