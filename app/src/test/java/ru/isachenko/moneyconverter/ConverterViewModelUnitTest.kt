package ru.isachenko.moneyconverter

import org.junit.Test

import org.junit.Assert.*
import ru.isachenko.moneyconverter.viewmodel.ConverterViewModel


class ConverterViewModelUnitTest {

    @Test
    fun `WHEN currency is null EXPECT 0`() {
        val viewModel = ConverterViewModel()

        val expected = 0.0
        viewModel.convert(null, 100.0)
        val actual = viewModel.conversionResult

        assertEquals(expected, actual, 0.000001)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN currency is 0 EXPECT illegal argument exception`() {
        val viewModel = ConverterViewModel()

        viewModel.convert(123.0, 0.0)
    }
}