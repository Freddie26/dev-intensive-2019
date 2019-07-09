package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String
{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date
{
    var time = this.time

    time += when (units)
        {
            TimeUnits.SECOND -> value * SECOND
            TimeUnits.MINUTE -> value * MINUTE
            TimeUnits.HOUR -> value * HOUR
            TimeUnits.DAY -> value * DAY
        }

    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String
{
    val seconds = ((date.time - this.time) / 1000).toInt()
    val minutes = (seconds / 60).toInt()
    val hours = (minutes / 60).toInt()
    val days = (hours / 24).toInt()

    return when
    {
        days < -360 -> "более чем через год"
        hours in -26*24..-26 -> "через ${TimeUnits.DAY.plural(days * -1)}"
        hours in -26..-22 -> "через день"
        minutes in -22*60..-75 -> "через ${TimeUnits.HOUR.plural(hours * -1)}"
        seconds in -4500..-2700 -> "через час"
        seconds in -2700..-75 -> "через ${TimeUnits.MINUTE.plural(minutes * -1)}"
        seconds in -75..-45 -> "через минуту"
        seconds in -45..-1 -> "через несколько секунд"
        seconds in -1..1 -> "только что"
        seconds in 1..45 -> "несколько секунд назад"
        seconds in 45..75 -> "минуту назад"
        seconds in 75..2700 -> "${TimeUnits.MINUTE.plural(minutes)} назад"
        minutes in 45..75 -> "час назад"
        minutes in 75..22*60 -> "${TimeUnits.HOUR.plural(hours)} назад"
        hours in 22..26 -> "день назад"
        hours in 26..24*360 -> "${TimeUnits.DAY.plural(days)} назад"
        days > 360 -> "более года назад"
        else -> throw Throwable("Ошибка")
    }
}

enum class TimeUnits
{
    SECOND
    {
        override fun plural(value: Int) = pluralEx(value, arrayOf("секунда", "секунды", "секунд"))
    },
    MINUTE
    {
        override fun plural(value: Int) = pluralEx(value, arrayOf("минута", "минуты", "минут"))
    },
    HOUR
    {
        override fun plural(value: Int) = pluralEx(value, arrayOf("час", "часа", "часов"))
    },
    DAY
    {
        override fun plural(value: Int) = pluralEx(value, arrayOf("день", "дня", "дней"))
    };

    abstract fun plural(value: Int): String

    protected fun pluralEx(value: Int, arr: Array<String>): String
    {
        val absValue = abs(value)
        val valueRem = abs(value % 10)
        return when
        {
            (absValue == 1) || (absValue > 20 && valueRem == 1) -> "$absValue  ${arr[0].toString()}"
            (absValue in 2..4) || (valueRem in 2..4) -> "$absValue ${arr[1].toString()}"
            else -> "$absValue ${arr[2].toString()}"
//                (absValue in 5..20) || (valueRem in 5..9) || (valueRem == 0) -> ""
        }
    }
}