package com.example.recipemanager.ui.recipe

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.ui.EDIT_RECIPE_ACTIVITY_REQUEST_CODE
import com.example.recipemanager.ui.RECIPE_EXTRA
import kotlinx.android.synthetic.main.activity_view_recipe.*

class ViewRecipeActivity : AppCompatActivity() {

    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_recipe)


        initViews()
    }

    private fun initViews(){
        recipe = intent.getParcelableExtra(RECIPE_EXTRA)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = recipe.title

        tvRecipeDescription.text = recipe.description
        tvRecipeIngredients.text = recipe.ingredients
        tvRecipeInstructions.text = recipe.instructions

        tvCreated.text = getString(R.string.created, recipe.created.toString())
        tvLastUpdated.text = getString(R.string.last_updated, recipe.lastUpdated.toString())
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_edit ->{
                val intent = Intent(this, EditRecipeActivity::class.java)
                intent.putExtra(RECIPE_EXTRA, recipe)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivityForResult(intent, EDIT_RECIPE_ACTIVITY_REQUEST_CODE)
                true
            }
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                EDIT_RECIPE_ACTIVITY_REQUEST_CODE -> {
                    supportActionBar?.title = recipe.title
                    tvRecipeDescription.text = recipe.description
                    tvRecipeIngredients.text = recipe.ingredients
                    tvRecipeInstructions.text = recipe.instructions
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}
