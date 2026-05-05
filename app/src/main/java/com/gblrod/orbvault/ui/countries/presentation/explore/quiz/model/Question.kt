package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.model

data class Question(
    val text: Int,
    val desc: Int,
    val args: List<Any>,
    val options: List<String>,
    val optionCorrect: Int
)