package com.example.recipemanager.model

import androidx.room.Entity
import androidx.room.ForeignKey

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
){
    override fun toString(): String {
        return "$recipeId in $collectionId"
    }
}