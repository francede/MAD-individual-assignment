package com.example.recipemanager.ui.recipecollection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recipemanager.database.RecipeRepository
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewCollectionViewModel(application: Application, collectionId: Long) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val recipeRepository = RecipeRepository(application.applicationContext)
    val recipes: LiveData<List<Recipe>> = recipeRepository.getRecipesInCollection(collectionId)

    fun deleteRecipeInCollection(recipeId: Long, collectionId: Long){
        ioScope.launch {
            recipeRepository.deleteRecipeInCollection(recipeId, collectionId)
        }
    }

    fun updateCollection(collection: RecipeCollection){
        ioScope.launch {
            recipeRepository.updateCollection(collection)
        }
    }
}