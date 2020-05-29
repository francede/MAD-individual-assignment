package com.example.recipemanager

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection

@Dao
interface RecipesDAO {
    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): LiveData<List<Recipe>>

    //@Query("SELECT * FROM recipe WHERE recipeId=:id")
    //fun getRecipe(id: Int): LiveData<Recipe>

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("SELECT * FROM RecipeCollection")
    fun getAllCollections(): LiveData<List<RecipeCollection>>

    //@Query("SELECT * FROM RecipeCollection WHERE collectionId=:id")
    //fun getCollection(id: Int): LiveData<RecipeCollection>

    @Insert
    suspend fun insertCollection(collection: RecipeCollection)

    @Delete
    suspend fun deleteCollection(collection: RecipeCollection)

    @Update
    suspend fun updateCollection(collection: RecipeCollection)

    @Transaction
    @Query("SELECT * FROM recipecollection WHERE collectionId=:id")
    fun getCollectionWithRecipes(id: Int): LiveData<List<RecipeCollection>>
}