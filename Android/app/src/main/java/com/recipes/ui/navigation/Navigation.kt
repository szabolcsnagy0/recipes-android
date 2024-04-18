package com.recipes.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.recipes.ui.recipedetails.RecipeDetails
import com.recipes.ui.recipeslist.ListViewModel
import com.recipes.ui.recipeslist.RecipesList

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val recipeViewModel = remember { ListViewModel() }

    NavHost(navController = navController, startDestination = Routes.RecipeList.route) {
        composable(route = Routes.RecipeList.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            }
        ) {
            RecipesList(
                viewModel = recipeViewModel
            ) {
                recipeViewModel.selectedRecipe.value?.let {
                    navController.navigate(Routes.RecipeDetails.route) {
                        popUpTo(Routes.RecipeDetails.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable(route = Routes.RecipeDetails.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            }) {
            recipeViewModel.selectedRecipe.value?.let { recipe ->
                RecipeDetails(
                    recipe = recipe
                ) {
                    navController.navigate(Routes.RecipeList.route) {
                        popUpTo(Routes.RecipeList.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}