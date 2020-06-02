package com.example.recipemanager.ui.recipecollection

import androidx.lifecycle.ViewModel
import android.app.Application
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class has to be implemented to pass arguments to viewmodel
 */
class ViewCollectionViewModelFactory(private val mApplication: Application, private val mCollectionId: Long)
    : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewCollectionViewModel(mApplication, mCollectionId) as T
    }
}