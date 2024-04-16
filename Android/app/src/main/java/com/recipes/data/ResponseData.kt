package com.recipes.data

data class ResponseData(
val limit: Int?,
val recipes: List<RecipeData?>?,
val skip: Int?,
val total: Int?
)
