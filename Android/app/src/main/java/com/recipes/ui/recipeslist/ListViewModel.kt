package com.recipes.ui.recipeslist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recipes.api.ApiService
import com.recipes.data.RecipeData
import com.recipes.data.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {

    private var _recipes = MutableLiveData<List<RecipeData?>>(
        emptyList()
    )
    var recipes = _recipes

    val selectedRecipe: MutableState<RecipeData?> = mutableStateOf(null)

    var searchQuery = MutableLiveData("")
        set(newValue) {
            field = newValue
            _recipes.value?.filter { recipe ->
                newValue.value?.let { query ->
                    recipe?.name?.contains(
                        query,
                        ignoreCase = true
                    )
                } ?: true
            }?.also {
                recipes.value = emptyList()
            }
        }

    init {
        fetchRecipes()
    }

    fun selectItem(recipeData: RecipeData) {
        selectedRecipe.value = recipeData
    }

    private fun fetchRecipes(onResult: (Boolean, String?) -> Unit = { _, _ -> }) {
        val call = ApiService.api.getRecipes()
        call?.enqueue(object : Callback<ResponseData?> {

            override fun onResponse(
                call: Call<ResponseData?>,
                response: Response<ResponseData?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.recipes?.let {
                        _recipes.value = it
                    }
                    Log.i("get-recipe", response.body().toString())
                    onResult(true, null)
                } else {
                    Log.e("get-recipes", "${response.code()} ${response.message()}")
                    onResult(false, "Hiba! ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                Log.e("get-recipe", t.message.toString())
                onResult(false, "Hiba! ${t.message.toString()}!")
            }
        })
    }
}