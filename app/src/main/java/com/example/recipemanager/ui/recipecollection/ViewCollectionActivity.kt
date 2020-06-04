package com.example.recipemanager.ui.recipecollection

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
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
import kotlinx.android.synthetic.main.popup_rename_collection.view.*

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_edit ->{
                val popup = AlertDialog.Builder(this)
                popup.setTitle(getString(R.string.rename_collection))

                val popupView = LayoutInflater.from(this).inflate(R.layout.popup_rename_collection,null)
                popupView.etCollectionName.setText(collection.title)

                popup.setView(popupView)

                popup.setPositiveButton(getString(R.string.rename)){ _, _ ->
                    collection.title = popupView.etCollectionName.text.toString()
                    viewModel.updateCollection(collection)
                    supportActionBar?.title = collection.title
                }

                popup.setNegativeButton(getString(R.string.cancel)){ dialog, _ ->
                    dialog.dismiss()
                }

                popup.show()
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
