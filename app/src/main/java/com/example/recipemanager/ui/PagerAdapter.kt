package com.example.recipemanager.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipemanager.ui.recipecollection.CollectionFragment
import com.example.recipemanager.ui.recipes.RecipesFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return when(position) {
            0 -> RecipesFragment()
            1 -> CollectionFragment()
            else -> RecipesFragment()
        }
    }
}