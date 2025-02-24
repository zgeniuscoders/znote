package cd.zgeniuscoders.znote.note.data

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun toDateTime(timestamp:Long): String {
    val  dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
    val month =  dateTime.monthValue
    val dayOfYear  =dateTime.dayOfMonth
    val  year =  dateTime.year

    return "$dayOfYear $month $year"
}

fun toTimestamp(date: String): Long {
    val formater =  DateTimeFormatter.ofPattern("dd MM yyyy")
    val localDate = LocalDate.parse(date, formater)

    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun getCurrentDate(): String {
    val formater =  DateTimeFormatter.ofPattern("dd MM yyyy")
    return LocalDate.now().format(formater).toString()
}