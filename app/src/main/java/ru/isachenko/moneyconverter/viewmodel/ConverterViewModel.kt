package ru.isachenko.moneyconverter.viewmodel

import androidx.lifecycle.ViewModel

class ConverterViewModel : ViewModel() {

    var convertionResult: Double = 0.0

    fun convert(valueFrom: Double?, currency: Double?) {
        if (null == valueFrom) {
            convertionResult = 0.0
            return
        }
        if (null != currency) {
            convertionResult = valueFrom / currency
        }
    }
}