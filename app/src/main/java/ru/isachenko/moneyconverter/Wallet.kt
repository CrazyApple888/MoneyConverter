package ru.isachenko.moneyconverter

data class Wallet(
    val charCode: String,
    val name: String,
    val value: Double,
    val previousValue: Double
)
