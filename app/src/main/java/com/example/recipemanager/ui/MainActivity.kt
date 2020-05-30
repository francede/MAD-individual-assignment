package com.example.recipemanager.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.recipemanager.ui.recipecollection.CreateCollectionActivity
import com.example.recipemanager.ui.recipes.EditRecipeActivity
import com.example.recipemanager.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews(){
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = PagerAdapter(this)

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager){ tab,position ->
            when(position){
                0 -> tab.text = "Recipes"
                1 -> tab.text = "Collections"
            }
        }.attach()

        fab.setOnClickListener{
            when(viewPager.currentItem){
                0->{
                    val intent = Intent(this, EditRecipeActivity::class.java)
                    startActivityForResult(intent, EditRecipeActivity.EDIT_RECIPE_ACTIVITY_REQUEST_CODE)
                }
                1->{
                    val intent = Intent(this, CreateCollectionActivity::class.java)
                    startActivityForResult(intent, CreateCollectionActivity.CREATE_COLLECTION_REQUEST_CODE)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the back button.
            super.onBackPressed()
        } else {
            // Otherwise, select first step
            viewPager.currentItem = 0
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.fragments.forEach{
            it.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
