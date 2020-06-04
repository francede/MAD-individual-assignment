package com.example.recipemanager.ui.addtocollection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recipemanager.database.RecipeRepository
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.model.RecipeInCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddToCollectionViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val recipeRepository = RecipeRepository(application.applicationContext)
    val collections = recipeRepository.getAllCollections()


    //Inserts RecipeToCollection unless collection or recipe are not persisted or if recipeincollection already exists
    fun addRecipeToCollection(recipe: Recipe, collection: RecipeCollection){
        collection.collectionId ?: return
        recipe.recipeId ?: return

        val recipeInCollection = RecipeInCollection(recipe.recipeId, collection.collectionId)
        ioScope.launch {
            if(recipeRepository.getRecipeInCollection(recipe.recipeId, collection.collectionId) == null){
                recipeRepository.insertRecipeInCollection(recipeInCollection)
            }
        }
    }

    fun insertCollection(collection: RecipeCollection): Long?{
        var id: Long? = null
        ioScope.launch {
            id = recipeRepository.insertCollection(collection)
        }
        return id
    }
}