package com.example.recipemanager.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Recipe(
    var title: String,
    var description: String,
    var ingredients: String,
    var instructions: String,
    var created: Date = Date(),
    var lastUpdated: Date = Date(),
    @PrimaryKey(autoGenerate = true)
    var recipeId: Long? = null
): Parcelable{
    override fun toString(): String {
        return "$recipeId: $title"
    }
}