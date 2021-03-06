package com.example.recipemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.model.RecipeInCollection

@Database(entities = [Recipe::class, RecipeCollection::class, RecipeInCollection::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipesDAO

    companion object {
        private const val DATABASE_NAME = "SHOPPING_LIST_DATABASE"

        @Volatile
        private var recipeRoomDatabaseInstance: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase? {
            if (recipeRoomDatabaseInstance == null) {
                synchronized(RecipeDatabase::class.java) {
                    if (recipeRoomDatabaseInstance == null) {
                        recipeRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                RecipeDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return recipeRoomDatabaseInstance
        }
    }

}
