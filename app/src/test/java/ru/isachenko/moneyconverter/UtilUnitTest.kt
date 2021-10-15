package ru.isachenko.moneyconverter

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.isachenko.moneyconverter.model.Wallet
import ru.isachenko.moneyconverter.util.Util

class UtilUnitTest {

    private val hardcodedShortenJson = JSONObject(
        "{\n" +
                "    \"Date\": \"2021-10-16T11:30:00+03:00\",\n" +
                "    \"PreviousDate\": \"2021-10-15T11:30:00+03:00\",\n" +
                "    \"PreviousURL\": \"\\/\\/www.cbr-xml-daily.ru\\/archive\\/2021\\/10\\/15\\/daily_json.js\",\n" +
                "    \"Timestamp\": \"2021-10-15T16:00:00+03:00\",\n" +
                "    \"Valute\": {\n" +
                "        \"AUD\": {\n" +
                "            \"ID\": \"R01010\",\n" +
                "            \"NumCode\": \"036\",\n" +
                "            \"CharCode\": \"AUD\",\n" +
                "            \"Nominal\": 1,\n" +
                "            \"Name\": \"Австралийский доллар\",\n" +
                "            \"Value\": 52.8508,\n" +
                "            \"Previous\": 53.2211\n" +
                "        },\n" +
                "        \"AZN\": {\n" +
                "            \"ID\": \"R01020A\",\n" +
                "            \"NumCode\": \"944\",\n" +
                "            \"CharCode\": \"AZN\",\n" +
                "            \"Nominal\": 1,\n" +
                "            \"Name\": \"Азербайджанский манат\",\n" +
                "            \"Value\": 41.9288,\n" +
                "            \"Previous\": 42.2511\n" +
                "        }\n" +
                "}\n" +
                "}"
    )

    @Test
    fun `WHEN broken json and empty preferred currencies EXPECT empty list`() {
        val testPreferredCurrencies: List<String> = mock()
        whenever(testPreferredCurrencies.contains(any())).thenReturn(false)
        val testJson: JSONObject = mock()
        whenever(testJson.getJSONObject(anyString())).thenReturn(JSONObject("{}"))

        val actual = Util.parseJSON(testJson, testPreferredCurrencies)

        val expected = emptyList<Wallet>()

        assertEquals(expected, actual)
    }

    @Test
    fun `WHEN normal json and normal preferred currencies EXPECT list of wallets with preferred currencies on top`() {
        val preferredCurrencies = listOf("AZN")
        val actual = Util.parseJSON(hardcodedShortenJson, preferredCurrencies)

        val expected = listOf(
            Wallet(
                "AZN",
                "Азербайджанский манат",
                41.9288,
                42.2511
            ),
            Wallet(
                "AUD",
                "Австралийский доллар",
                52.8508,
                53.2211
            )
        )


        assertEquals(expected, actual)
    }


    @Test
    fun `WHEN normal json and empty preferred currencies EXPECT list of wallets with order from json`() {
        val actual = Util.parseJSON(hardcodedShortenJson, emptyList())

        val expected = listOf(
            Wallet(
                "AUD",
                "Австралийский доллар",
                52.8508,
                53.2211
            ),
            Wallet(
                "AZN",
                "Азербайджанский манат",
                41.9288,
                42.2511
            )
        )

        assertEquals(expected, actual)
    }
}