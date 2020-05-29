package com.example.recipemanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.model.RecipeInCollection

@Database(entities = [Recipe::class, RecipeCollection::class, RecipeInCollection::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipesDAO

    companion object {
        private const val DATABASE_NAME = "SHOPPING_LIST_DATABASE"

        @Volatile
        private var shoppingListRoomDatabaseInstance: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase? {
            if (shoppingListRoomDatabaseInstance == null) {
                synchronized(RecipeDatabase::class.java) {
                    if (shoppingListRoomDatabaseInstance == null) {
                        shoppingListRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,RecipeDatabase::class.java, DATABASE_NAME).build()
                    }
                }
            }
            return shoppingListRoomDatabaseInstance
        }
    }

}
