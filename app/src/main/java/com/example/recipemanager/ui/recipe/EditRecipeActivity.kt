package com.example.recipemanager.ui.recipe

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.ui.RECIPE_EXTRA
import com.example.recipemanager.ui.recipecollectionlist.RecipeCollectionListViewModel
import kotlinx.android.synthetic.main.activity_edit_recipe.*

class EditRecipeActivity : AppCompatActivity() {

    private lateinit var viewModel: EditRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        viewModel = ViewModelProvider(this).get(EditRecipeViewModel::class.java)
        initViews()
    }

    private fun initViews(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.edit_recipe_activity_title)

        val recipe = intent.getParcelableExtra<Recipe>(RECIPE_EXTRA)
        if(recipe != null){
            etRecipeTitle.setText(recipe.title)
            etRecipeDescription.setText(recipe.description)
            etRecipeIngredients.setText(recipe.ingredients)
            etRecipeInstructions.setText(recipe.instructions)
        }
    }

    private fun startViewRecipeActivity(recipe: Recipe?){
        val intent = Intent(this, ViewRecipeActivity::class.java)
        intent.putExtra(RECIPE_EXTRA, recipe)

        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_recipe_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save ->{
                if(etRecipeTitle.text.toString().isBlank()){
                    Toast.makeText(this,"Recipe must have a title",Toast.LENGTH_SHORT).show()
                    true
                }else{
                    val recipe = Recipe(
                        etRecipeTitle.text.toString(),
                        etRecipeDescription.text.toString(),
                        etRecipeIngredients.text.toString(),
                        etRecipeInstructions.text.toString(),
                        intent.getParcelableExtra<Recipe>(RECIPE_EXTRA)?.recipeId
                    )

                    if(recipe.recipeId == null) viewModel.insertRecipe(recipe)
                    else viewModel.updateRecipe(recipe)

                    startViewRecipeActivity(recipe)

                    true
                }
            }
            android.R.id.home ->{
                startViewRecipeActivity(intent.getParcelableExtra(RECIPE_EXTRA))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
