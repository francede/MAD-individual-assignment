package com.example.recipemanager.ui.recipecollectionlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recipemanager.database.RecipeRepository
import com.example.recipemanager.model.RecipeCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeCollectionListViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val recipeRepository =
        RecipeRepository(application.applicationContext)
    val collections: LiveData<List<RecipeCollection>> = recipeRepository.getAllCollections()

    fun insertCollection(collection: RecipeCollection){
        ioScope.launch {
            recipeRepository.insertCollection(collection)
        }
    }

    fun updateCollection(collection: RecipeCollection){
        ioScope.launch {
            recipeRepository.updateCollection(collection)
        }
    }

    fun deleteCollection(collection: RecipeCollection){
        ioScope.launch {
            recipeRepository.deleteCollection(collection)
        }
    }

}