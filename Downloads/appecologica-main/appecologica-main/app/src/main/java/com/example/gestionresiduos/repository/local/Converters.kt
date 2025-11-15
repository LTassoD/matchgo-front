package com.example.gestionresiduos.repository.local

import com.example.gestionresiduos.model.TypeConverter
import java.time.LocalDate

object Converters {
    fun dateToString(date: LocalDate?) = TypeConverter.fromDate(date)
    fun stringToDate(value: String?) = TypeConverter.toDate(value)
}
