package com.example.recipemanager.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.model.RecipeInCollection

class RecipeRepository(context: Context) {
    private var recipesDAO: RecipesDAO

    init {
        val reminderRoomDatabase =
            RecipeDatabase.getDatabase(context)
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

    fun getCollection(collectionId: Long): LiveData<RecipeCollection>{
        return recipesDAO.getCollection(collectionId)
    }

    suspend fun insertCollection(collection: RecipeCollection): Long{
        return recipesDAO.insertCollection(collection)
    }


    suspend fun deleteCollection(collection: RecipeCollection){
        recipesDAO.deleteCollection(collection)
    }


    suspend fun updateCollection(collection: RecipeCollection){
        recipesDAO.updateCollection(collection)
    }

    suspend fun getRecipeInCollection(recipeId: Long, collectionId: Long): RecipeInCollection{
        return recipesDAO.getRecipeInCollection(recipeId, collectionId)
    }

    fun getRecipesInCollection(collectionId: Long): LiveData<List<Recipe>>{
        return recipesDAO.getRecipesInCollection(collectionId)
    }

    suspend fun insertRecipeInCollection(recipeInCollection: RecipeInCollection){
        recipesDAO.insertRecipeInCollection(recipeInCollection)
    }

    suspend fun deleteRecipeInCollection(recipeId: Long, collectionId: Long){
        recipesDAO.deleteRecipeInCollection(recipeId, collectionId)
    }
}
