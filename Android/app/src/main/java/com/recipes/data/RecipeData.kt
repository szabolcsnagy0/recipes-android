package com.recipes.data

import com.google.gson.annotations.JsonAdapter
import com.recipes.api.EnumDeserializer

data class RecipeData(
    val caloriesPerServing: Int? = null,
    val cookTimeMinutes: Int? = null,
    val cuisine: String? = null,
    @JsonAdapter(EnumDeserializer::class)
    val difficulty: Difficulty? = null,
    val id: Int? = null,
    val image: String? = null,
    val ingredients: List<String?>? = null,
    val instructions: List<String?>? = null,
    val mealType: List<String?>? = null,
    val name: String? = null,
    val prepTimeMinutes: Int? = null,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val servings: Int? = null,
    val tags: List<String?>? = null
)