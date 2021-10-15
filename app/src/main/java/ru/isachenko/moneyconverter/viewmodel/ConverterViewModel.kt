package ru.isachenko.moneyconverter.viewmodel

import androidx.lifecycle.ViewModel

class ConverterViewModel : ViewModel() {

    var conversionResult: Double = 0.0
    var charCode: String = ""

    fun convert(valueFrom: Double?, currency: Double?) {
        if (null == valueFrom) {
            conversionResult = 0.0
            return
        }
        if (null != currency) {
            if (0.0 >= currency) {
                conversionResult = 0.0
                throw IllegalArgumentException()
            }
            conversionResult = valueFrom / currency
        }
    }
}