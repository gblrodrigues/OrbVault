package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data.generator

import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.model.Question
import java.text.NumberFormat
import java.util.Locale

class AreaQuestion : QuestionGenerator {
    override fun generate(
        countries: List<CountriesDto>
    ): Question {
        val region = countries.random().region
        val filtered = countries.filter { it.region == region  && it.area != null}
        val safeList = if (filtered.size >= 4) {
            filtered.shuffled().take(n = 4)
        } else {
            countries.shuffled().take(n = 4)
        }

        val correct = safeList.maxBy { it.area ?: 0.0 }
        val area = correct.area ?: 0.0
        val formattedArea = NumberFormat
            .getNumberInstance(Locale.getDefault())
            .format(area)

        return Question(
            text = R.string.quiz_question_area,
            desc = R.string.quiz_question_area_result,
            args = listOf(
                correct.name.common,
                formattedArea
            ),
            options = safeList.map { it.name.common },
            optionCorrect = safeList.indexOf(correct)
        )
    }
}