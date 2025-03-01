package cd.zgeniuscoders.znote.note.data

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun toDateTime(timestamp:Long): String {
    val  dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
    val month =  dateTime.monthValue
    val dayOfYear  =dateTime.dayOfMonth
    val  year =  dateTime.year

    return "$dayOfYear $month $year"
}

fun toTimestamp(noteDate: String): Long {
    val formater = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
    val date: Date? = formater.parse(noteDate)
    return date?.time ?: 0
}

fun getCurrentDate(): String {
    val formater =  DateTimeFormatter.ofPattern("dd MM yyyy")
    return LocalDate.now().format(formater).toString()
}