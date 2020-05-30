package com.example.recipemanager

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection

class RecipeRepository(context: Context) {
    private var recipesDAO: RecipesDAO

    init {
        val reminderRoomDatabase = RecipeDatabase.getDatabase(context)
        recipesDAO = reminderRoomDatabase!!.recipeDao()
    }

    fun getAllRecipes(): LiveData<List<Recipe>>{
        return recipesDAO.getAllRecipes()
    }

    suspend fun insertRecipe(recipe: Recipe){
        recipesDAO.insertRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe){
        recipesDAO.deleteRecipe(recipe)
    }


    suspend fun updateRecipe(recipe: Recipe){
        recipesDAO.updateRecipe(recipe)
    }


    fun getAllCollections(): LiveData<List<RecipeCollection>>{
        return recipesDAO.getAllCollections()
    }

    suspend fun insertCollection(collection: RecipeCollection){
        recipesDAO.insertCollection(collection)
    }


    suspend fun deleteCollection(collection: RecipeCollection){
        recipesDAO.deleteCollection(collection)
    }


    suspend fun updateCollection(collection: RecipeCollection){
        recipesDAO.updateCollection(collection)
    }


    fun getRecipesInCollection(id: Long): LiveData<List<Recipe>>{
        return recipesDAO.getRecipesInCollection(id)
    }

}
