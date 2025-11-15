package com.example.gestionresiduos.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Conversores simples para persistir tipos que Room/SharedPrefs no soportan directamente
object TypeConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun fromDate(date: LocalDate?): String? = date?.format(formatter)

    fun toDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it, formatter) }

    fun fromList(list: List<String>?): String? = list?.joinToString("|")

    fun toList(value: String?): List<String> = value?.split("|")?.filter { it.isNotBlank() } ?: emptyList()
}
