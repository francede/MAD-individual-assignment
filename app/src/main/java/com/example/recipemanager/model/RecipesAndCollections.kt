package com.example.recipemanager.model

import androidx.room.*

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long,
    val title: String,
    val description: String,
    val ingredients: String,
    val instructions: String
)

@Entity
data class RecipeCollection(
    @PrimaryKey(autoGenerate = true)
    val collectionId: Long,
    val title: String
)

@Entity(primaryKeys = ["recipeId","collectionId"], foreignKeys = [
    ForeignKey(
        entity = Recipe::class,
        parentColumns = ["recipeId"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = RecipeCollection::class,
        parentColumns = ["collectionId"],
        childColumns = ["collectionId"],
        onDelete = ForeignKey.CASCADE
    )])
data class RecipeInCollection(
    val recipeId: Long,
    val collectionId: Long
)
