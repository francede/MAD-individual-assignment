package com.example.recipemanager.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipemanager.ui.recipecollectionlist.RecipeCollectionListFragment
import com.example.recipemanager.ui.recipelist.RecipeListFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    lateinit var recipeListFragment: RecipeListFragment
    lateinit var collectionListFragment: RecipeCollectionListFragment

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        var f: Fragment
        return when(position) {
            0 -> {
                f = RecipeListFragment()
                recipeListFragment = f
                f
            }
            1 -> {
                f = RecipeCollectionListFragment()
                collectionListFragment = f
                f
            }
            else -> {
                f = RecipeListFragment()
                recipeListFragment = f
                f
            }
        }
    }
}