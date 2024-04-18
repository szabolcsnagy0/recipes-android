package com.recipes.ui.navigation

sealed class Routes(val route: String) {
    data object RecipeDetails : Routes("recipe_details")
    data object RecipeList : Routes("recipe_list")
}