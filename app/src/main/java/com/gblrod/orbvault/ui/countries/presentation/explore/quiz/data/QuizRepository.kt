package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data

import com.gblrod.orbvault.data.countries.remote.api.CountriesAPI
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data.generator.AreaQuestion
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data.generator.PopulationQuestion
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.model.Question

class QuizRepository(
    private val api: CountriesAPI
) {
    private val generators = listOf(
        PopulationQuestion(),
        AreaQuestion()
    )

    suspend fun getQuestions(): List<Question> {
        val countries = api.getAllCountries()

        return List(size = 5) {
            runCatching {
                generators.random().generate(countries)
            }.getOrNull()
        }.filterNotNull().shuffled()
    }
}