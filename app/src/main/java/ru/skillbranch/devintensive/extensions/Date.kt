package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong

const val SECOND = 1
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.YY"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(from: Date = Date()): String {
    val diff = this.time - from.time
    val absDiff = abs(diff)


    return when (absDiff) {
        in 0..1 -> "только что"
        in 1..45 -> if (diff < 0) "несколько секунд назад" else "через несколько секунд"
        in 45..75 -> if (diff < 0) "минуту назад" else "через минуту"
        in 75..45*MINUTE -> {
            var mins = Math.ceil(absDiff.toDouble() / MINUTE).toLong()
            mins = if (mins == 1L) mins + 1 else mins
            return if (diff < 0)
                "$mins ${Utils.plural(mins, "минуту", "минуты", "минут")} назад"
            else
                "через $mins ${Utils.plural(mins, "минуту", "минуты", "минут")}"
        }
        in 45*MINUTE..75*MINUTE -> if (diff < 0) "час назад" else "через час"
        in 75*MINUTE..22*HOUR -> {
            var hours = (absDiff.toDouble() / HOUR).roundToLong()
            hours = if (hours == 1L) hours + 1 else hours
            if (diff < 0)
                "$hours ${Utils.plural(hours, "час", "часа", "часов")} назад"
            else
                "через $hours ${Utils.plural(hours, "час", "часа", "часов")}"
        }
        in 22*HOUR..26*HOUR -> if (diff < 0) "день назад" else "через день"
        in 26*HOUR..360*DAY -> {
            var days = Math.ceil(absDiff.toDouble() / DAY).toLong()
            days = if (days == 1L) days + 1 else days
            if (diff < 0)
                "$days ${Utils.plural(days, "день", "дня", "дней")} назад"
            else
                "через $days ${Utils.plural(days, "день", "дня", "дней")}"
        }
        else -> if (diff < 0) "более года назад" else "более чем через год"
    }

//    val map = mapOf(
//        DAY to listOf("день", "дня", "дней"),
//        HOUR to listOf("час", "часа", "часов"),
//        MINUTE to listOf("минута", "минуты", "минут"),
//        SECOND to listOf("секунда", "секунды", "секунд")
//    )
//
//    map.forEach {
//        val count = abs(diff) / it.key
//        if (count > 0) {
//            val text = Utils.plural(count, it.value[0], it.value[1], it.value[2])
//            return if (diff > 0) "через $count $text" else "$count $text назад"
//        }
//    }
}

enum class TimeUnits {
    SECOND {
        override fun plural(count: Long): String {
            return "$count ${Utils.plural(count, "секунду", "секунды", "секунд")}"
        }
    },
    MINUTE {
        override fun plural(count: Long): String {
            return "$count ${Utils.plural(count, "минуту", "минуты", "минут")}"
        }
    },
    HOUR {
        override fun plural(count: Long): String {
            return "$count ${Utils.plural(count, "час", "часа", "часов")}"
        }
    },
    DAY {
        override fun plural(count: Long): String {
            return "$count ${Utils.plural(count, "день", "дня", "дней")}"
        }
    };

    abstract fun plural(count: Long): String
}

