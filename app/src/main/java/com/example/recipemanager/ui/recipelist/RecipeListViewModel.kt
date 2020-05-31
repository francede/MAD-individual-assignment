package com.example.recipemanager.ui.recipelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recipemanager.database.RecipeRepository
import com.example.recipemanager.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val recipeRepository =
        RecipeRepository(application.applicationContext)
    val recipes: LiveData<List<Recipe>> = recipeRepository.getAllRecipes()

    fun deleteRecipe(recipe: Recipe){
        ioScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }

}
