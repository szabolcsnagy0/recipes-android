package com.recipes.ui.recipeslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recipes.api.ApiService
import com.recipes.data.RecipeData
import com.recipes.data.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {
//    val recipes: MutableList<RecipeData?> = mutableListOf()

    private var _recipes = MutableLiveData<List<RecipeData?>>(
        emptyList()
    )
    var recipes = _recipes

    init {
        fetchRecipes()
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