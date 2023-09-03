package com.example.nuclearnote.domain.util

sealed class OrderType {

    object Asc : OrderType()
    object Desc : OrderType()
}