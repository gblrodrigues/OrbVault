package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data.generator

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.model.Question

interface QuestionGenerator {
    fun generate(
        countries: List<CountriesDto>
    ): Question
}