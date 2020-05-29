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

@Entity(primaryKeys = ["recipeId","collectionId"])
data class CollectionRecipeCrossRef(
    val recipeId: Long,
    val collectionId: Long
)

data class CollectionWithRecipes(
    @Embedded
    val collection: RecipeCollection,
    @Relation(parentColumn = "collectionId",
        entityColumn = "recipeId",
        associateBy = Junction(CollectionRecipeCrossRef::class)
    )
    val recipes: List<Recipe>
)

data class RecipesWithCollections(
    @Embedded
    val Recipe: Recipe,
    @Relation(parentColumn = "recipeId",
        entityColumn = "collectionId",
        associateBy = Junction(CollectionRecipeCrossRef::class)
    )
    val collections: List<RecipeCollection>
)