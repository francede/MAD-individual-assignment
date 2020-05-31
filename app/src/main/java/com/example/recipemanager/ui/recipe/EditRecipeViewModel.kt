package com.example.recipemanager.ui.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recipemanager.database.RecipeRepository
import com.example.recipemanager.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditRecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val recipeRepository = RecipeRepository(application.applicationContext)

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
}