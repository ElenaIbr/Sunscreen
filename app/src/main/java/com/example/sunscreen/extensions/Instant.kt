package com.example.sunscreen.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import java.util.Locale

fun Instant.toStringDate(
    format: FormatStyle = FormatStyle.LONG,
    zoneId: ZoneId = ZoneId.systemDefault()
): String? {
    val year = this.atZone(zoneId).year
    val month = this.atZone(zoneId).month
    val day = this.atZone(zoneId).dayOfMonth
    val localDate: LocalDate = LocalDate.of(year, month, day)
    val pattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
        format,
        null,
        IsoChronology.INSTANCE,
        Locale.getDefault(Locale.Category.FORMAT)
    )
    val dateFormatter = DateTimeFormatter.ofPattern(pattern)
    return try {
        localDate.format(dateFormatter)
    } catch (e: Exception) {
        null
    }
}
