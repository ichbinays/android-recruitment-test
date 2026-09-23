package data.mapper

import data.local.MarketEntity
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private val DATE_FORMATS = arrayOf(
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply { timeZone = TimeZone.getTimeZone("UTC") },
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply { timeZone = TimeZone.getTimeZone("UTC") },
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
)

private fun JSONObject.findFirstString(vararg keys: String): String? {
    for (key in keys) {
        val value = optString(key)
        if (value.isNotEmpty() && value != "null") return value
    }
    return null
}

fun JSONObject.toEntity(oldPrice: Double? = null): MarketEntity {
    val symbol = findFirstString("1", "symbol", "code", "name", "s", "ticker") ?: "UNKNOWN"

    val price = findFirstString("2", "price", "last", "p")?.toDoubleOrNull() ?: 0.0

    val direction = optString("0").lowercase()
    val previousPrice = oldPrice ?: price
    val changePrice = price - previousPrice

    val rawChangePercent = if (previousPrice > 0.0) (changePrice / previousPrice) * 100 else 0.0

    val finalChangePercent = when {
        rawChangePercent == 0.0 && oldPrice == null -> {
            when (direction) {
                "up" -> 0.05
                "down" -> -0.05
                else -> 0.0
            }
        }
        else -> rawChangePercent
    }
    val timeStr = findFirstString("7", "timestamp", "time")
    val timestamp = parseTimestamp(timeStr)

    return MarketEntity(
        symbol = symbol,
        price = price,
        timestamp = timestamp,
        changePrice = changePrice,
        changePercent = finalChangePercent
    )
}

private fun parseTimestamp(timeStr: String?): Long {
    if (timeStr.isNullOrEmpty()) return System.currentTimeMillis()

    timeStr.toLongOrNull()?.let { return it }

    for (format in DATE_FORMATS) {
        try {
            format.parse(timeStr)?.let { return it.time }
        } catch (_: Exception) {
        }
    }

    return System.currentTimeMillis()
}
