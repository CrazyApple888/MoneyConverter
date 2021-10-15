package ru.isachenko.moneyconverter

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.isachenko.moneyconverter.viewmodel.ConverterViewModel

@RunWith(Parameterized::class)
class ConverterViewModelParameterizedTest(
    private val valueFrom: Double,
    private val currency: Double,
    private val expected: Double
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(0.0, 1000.0, 0.0),
            arrayOf(123.0, 123.0, 1.0),
        )
    }

    @Test
    fun `WHEN convert EXPECT correct result`() {
        val viewModel = ConverterViewModel()

        viewModel.convert(valueFrom, currency)
        val actual = viewModel.conversionResult

        assertEquals(expected, actual, 0.000001)
    }
}