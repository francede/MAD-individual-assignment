package com.example.recipemanager.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.recipemanager.ui.recipecollection.CreateCollectionActivity
import com.example.recipemanager.ui.recipe.EditRecipeActivity
import com.example.recipemanager.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

const val RECIPE_EXTRA = "RECIPE_EXTRA"
const val COLLECTION_EXTRA = "COLLECTION_EXTRA"
const val EDIT_RECIPE_ACTIVITY_REQUEST_CODE = 100
const val CREATE_COLLECTION_REQUEST_CODE = 100


class MainActivity : AppCompatActivity(){

    private lateinit var viewPager: ViewPager2
    private lateinit var fabVisibilityListener: FloatingActionButton.OnVisibilityChangedListener
    private var fabShouldBeShown = true

    //Scroll listener to pass to fragments
    val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if(!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)){
                setFabVisibility(false)
            }else{
                setFabVisibility(true)
            }
        }
    }

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
                0 -> tab.text = getString(R.string.recipes)
                1 -> tab.text = getString(R.string.collections)
            }
        }.attach()

        fab.setOnClickListener{
            when(viewPager.currentItem){
                0->{
                    val intent = Intent(this, EditRecipeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivityForResult(intent, EDIT_RECIPE_ACTIVITY_REQUEST_CODE)
                }
                1->{
                    val intent = Intent(this, CreateCollectionActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivityForResult(intent, CREATE_COLLECTION_REQUEST_CODE)
                }
            }
        }

        fabVisibilityListener = object : FloatingActionButton.OnVisibilityChangedListener(){
            override fun onShown(fab: FloatingActionButton?) {
                super.onShown(fab)
                if(fabShouldBeShown) fab?.show()
            }

            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                if(!fabShouldBeShown) fab?.hide()
            }
        }

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setFabVisibility(true)
                }
            }
        )
    }

    fun setFabVisibility(isShown: Boolean){
        fabShouldBeShown = isShown
        if(isShown) fab.show(fabVisibilityListener)
        else fab.hide(fabVisibilityListener)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_sort ->{
                val sortFragment = SortFragment()
                sortFragment.show(supportFragmentManager, "sort")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
