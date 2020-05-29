package com.example.recipemanager

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection

@Dao
interface RecipesDAO {
    //RECIPES
    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    //COLLECTIONS
    @Query("SELECT * FROM RecipeCollection")
    fun getAllCollections(): LiveData<List<RecipeCollection>>

    @Insert
    suspend fun insertCollection(collection: RecipeCollection)

    @Delete
    suspend fun deleteCollection(collection: RecipeCollection)

    @Update
    suspend fun updateCollection(collection: RecipeCollection)

    //RECIPES IN COLLECTIONS
    @Query("""
        SELECT * FROM recipeincollection
        INNER JOIN recipe ON recipe.recipeId = recipeincollection.recipeId
        WHERE recipeincollection.collectionId = :collectionId
        """)
    fun getRecipesInCollection(collectionId: Long): LiveData<List<Recipe>>
}