package com.example.recipemanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class RecipeCollection(
    var title: String,
    //var created: Date = Date(),
    //var lastUpdated: Date = Date(),
    @PrimaryKey(autoGenerate = true)
    val collectionId: Long? = null
): Parcelable {
    override fun toString(): String {
        return "$collectionId: $title"
    }
}