package com.recipes.ui.recipeslist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
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

    val recipes: MutableLiveData<List<RecipeData?>>
        get() {
            if(searchQuery.value.isNullOrEmpty()) {
                return _recipes
            }
            return _recipes.value?.filter { recipe ->
                searchQuery.value?.let {
                    recipe?.name?.contains(
                        it,
                        ignoreCase = true
                    )
                } ?: true
            }.let {
                MutableLiveData(it)
            }
        }

    val selectedRecipe: MutableState<RecipeData?> = mutableStateOf(null)

    val searchQuery = MutableLiveData(String())

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
                    Log.e("get-recipe", "${response.code()} ${response.message()}")
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