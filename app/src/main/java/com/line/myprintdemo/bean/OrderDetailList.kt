package com.line.myprintdemo.bean

data class OrderDetailList(
    val isDiscount: Int,
    val amount: Double,
    val shopPrice: Double,
    val goodsName: String,
    val buyCount: Int,
)