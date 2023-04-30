package com.leit.booktracker.feature_bookshelf.domain.use_case

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class CalculatePagesToDate {

    operator fun invoke(
        currentDate: LocalDateTime,
        chosenDate:LocalDateTime,
        pages:Int
    ):Int {
        val daysBetween = ChronoUnit.DAYS.between(currentDate,chosenDate).toInt()
        return pages/daysBetween
    }

}