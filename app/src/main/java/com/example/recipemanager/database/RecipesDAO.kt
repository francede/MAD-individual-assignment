package com.example.recipemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.model.RecipeInCollection

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

    @Query("SELECT * FROM recipecollection WHERE collectionId=:collectionId")
    fun getCollection(collectionId: Long): LiveData<RecipeCollection>

    @Insert
    suspend fun insertCollection(collection: RecipeCollection): Long

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

    @Query("SELECT * FROM recipeincollection WHERE recipeId=:recipeId AND collectionId=:collectionId")
    suspend fun getRecipeInCollection(recipeId: Long, collectionId: Long): RecipeInCollection

    @Insert
    suspend fun insertRecipeInCollection(recipeInCollection: RecipeInCollection)

    @Query("DELETE FROM recipeincollection WHERE recipeId=:recipeId AND collectionId=:collectionId")
    suspend fun deleteRecipeInCollection(recipeId: Long, collectionId: Long)
}