package com.example.recipemanager.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.ui.recipecollectionlist.CreateCollectionActivity
import kotlinx.android.synthetic.main.activity_select_collection.*
import kotlinx.android.synthetic.main.fragment_recipe.*

class AddToCollectionActivity : AppCompatActivity() {

    private lateinit var viewModel: AddToCollectionViewModel
    private val collections = arrayListOf<RecipeCollection>()
    private lateinit var addToCollectionAdapter: AddToCollectionAdapter
    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_collection)

        recipe = intent.getParcelableExtra(RECIPE_EXTRA)!!
        addToCollectionAdapter = AddToCollectionAdapter(collections, this)
        viewModel = ViewModelProvider(this).get(AddToCollectionViewModel::class.java)

        initViews()
        observeViewModel()
    }

    private fun initViews(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add ${recipe.title} to collection"

        rvCollections.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvCollections.adapter = addToCollectionAdapter
        rvCollections.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        btnAddCollection.setOnClickListener{
            val intent = Intent(this, CreateCollectionActivity::class.java)
            startActivityForResult(intent, CREATE_COLLECTION_REQUEST_CODE)
        }
    }

    private fun observeViewModel(){
        viewModel.collections.observe(this, Observer {recipes ->
            this.collections.clear()
            this.collections.addAll(recipes)
            addToCollectionAdapter.notifyDataSetChanged()
        })
    }

    fun addToCollection(recipeCollection: RecipeCollection){
        viewModel.addRecipeToCollection(recipe, recipeCollection)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                CREATE_COLLECTION_REQUEST_CODE ->{
                    val collection = data?.getParcelableExtra<RecipeCollection>(COLLECTION_EXTRA)
                    if(collection != null){
                        Log.e("coll", collection.toString())
                        viewModel.insertCollection(collection)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
