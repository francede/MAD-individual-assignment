package com.example.recipemanager.ui.recipecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.ui.COLLECTION_EXTRA
import kotlinx.android.synthetic.main.fragment_recipe.*

class ViewCollectionActivity : AppCompatActivity() {

    private lateinit var collection: RecipeCollection
    private lateinit var viewModel: ViewCollectionViewModel
    private val recipes = arrayListOf<Recipe>()
    private lateinit var recipeAdapter: ViewCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_collection)

        collection = intent.getParcelableExtra(COLLECTION_EXTRA)!!
        recipeAdapter = ViewCollectionAdapter(recipes, this, collection)
        viewModel = ViewModelProvider(this).get(ViewCollectionViewModel::class.java)

        initViews()
        observeViewModel()
    }

    private fun initViews(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = collection.title

        rvRecipes.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvRecipes.adapter = recipeAdapter
        rvRecipes.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun observeViewModel(){
        viewModel.recipes.observe(this, Observer {recipes ->
            this.recipes.clear()
            this.recipes.addAll(recipes)
            recipeAdapter.notifyDataSetChanged()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_edit ->{
                //TODO
                true
            }
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
