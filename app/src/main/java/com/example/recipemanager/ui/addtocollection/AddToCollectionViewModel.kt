package com.example.recipemanager.ui.addtocollection

import android.app.Application
import android.util.Log
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

        val recipeInCollection = RecipeInCollection(recipe.recipeId!!, collection.collectionId!!)
        ioScope.launch {
            if(recipeRepository.getRecipeInCollection(recipe.recipeId!!, collection.collectionId!!) == null){
                recipeRepository.insertRecipeInCollection(recipeInCollection)
            }
        }
    }

    //Inserts collection and adds recipe to that collection
    fun insertRecipeToNewCollection(recipe: Recipe, collection: RecipeCollection){
        ioScope.launch {
            collection.collectionId = recipeRepository.insertCollection(collection)
            if(recipeRepository.getRecipeInCollection(recipe.recipeId!!, collection.collectionId!!) == null){
                recipeRepository.insertRecipeInCollection(
                    RecipeInCollection(recipe.recipeId!!, collection.collectionId!!)
                )
            }
        }
    }
}