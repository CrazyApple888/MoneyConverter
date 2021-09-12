package ru.isachenko.moneyconverter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_table")
data class Wallet(
    @PrimaryKey val charCode: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "value") val value: Double
)