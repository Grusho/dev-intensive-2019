package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 60
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val MONTH = 30 * DAY
const val YEAR = 365 * DAY

fun Date.format(pattern: String = "HH:mm:ss dd.MM.YY"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        TimeUnits.MONTH -> value * MONTH
        TimeUnits.YEAR -> value * YEAR
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(from: Date = Date()): String {
    val diff = this.time - from.time
    if (abs(diff) / YEAR > 0)
        return if (diff > 0) "более чем через год" else "более года назад"

    val map = mapOf(
        MONTH to listOf("месяц", "месяца", "месяцев"),
        DAY to listOf("день", "дня", "дней"),
        HOUR to listOf("час", "часа", "часов"),
        MINUTE to listOf("минута", "минуты", "минут"),
        SECOND to listOf("секунда", "секунды", "секунд")
    )
    map.forEach {
        val count = abs(diff) / it.key
        if (count > 0) {
            val text = Utils.plural(count, it.value[0], it.value[1], it.value[2])
            return if (diff > 0) "через $count $text" else "$count $text назад"
        }
    }

    return "только что"
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY,
    MONTH,
    YEAR
}

