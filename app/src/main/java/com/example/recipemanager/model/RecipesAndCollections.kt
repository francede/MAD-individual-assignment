package com.example.recipemanager.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Recipe(
    val title: String,
    val description: String,
    val ingredients: String,
    val instructions: String,
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long? = null
): Parcelable{
    override fun toString(): String {
        return "$recipeId: $title"
    }
}

@Entity
data class RecipeCollection(
    val title: String,

    @PrimaryKey(autoGenerate = true)
    val collectionId: Long? = null
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
