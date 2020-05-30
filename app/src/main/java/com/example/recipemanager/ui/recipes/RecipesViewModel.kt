package com.example.recipemanager.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recipemanager.RecipeRepository
import com.example.recipemanager.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val recipeRepository = RecipeRepository(application.applicationContext)
    val recipes: LiveData<List<Recipe>> = recipeRepository.getAllRecipes()

    fun insertRecipe(recipe: Recipe){
        ioScope.launch {
            recipeRepository.insertRecipe(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe){
        ioScope.launch {
            recipeRepository.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe){
        ioScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }

}
