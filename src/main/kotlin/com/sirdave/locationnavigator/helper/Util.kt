package com.sirdave.locationnavigator.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

inline fun <reified T : Enum<T>> getEnumName(name: String): T {
    check (isValidEnum<T>(name)){
        throw IllegalStateException("Invalid ${T::class.simpleName} name")
    }
    return enumValueOf(name.uppercase())
}

inline fun <reified T : Enum<T>> isValidEnum(name: String): Boolean {
    return enumValues<T>().any { enum -> enum.name.equals(name, ignoreCase = true) }
}


fun LocalDateTime.toFormattedDate(): String{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return this.format(formatter)
}

fun String.toLocalDateTime(): LocalDateTime{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return LocalDateTime.parse(this, formatter)
}