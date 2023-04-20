package com.leit.booktracker.feature_bookshelf.domain.use_case

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CalculatePagesToDate {

    operator fun invoke(
        currentDate:LocalDate,
        chosenDate:LocalDate,
        pages:Int
    ):Int {
        val daysBetween = ChronoUnit.DAYS.between(currentDate,chosenDate).toInt()
        return pages/daysBetween
    }

}