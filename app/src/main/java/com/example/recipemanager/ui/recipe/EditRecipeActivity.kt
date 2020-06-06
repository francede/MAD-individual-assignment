package com.example.recipemanager.ui.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.ui.RECIPE_EXTRA
import kotlinx.android.synthetic.main.activity_edit_recipe.*
import java.util.*

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
        supportActionBar?.setTitle(R.string.edit_recipe)

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
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save ->{
                if(etRecipeTitle.text.toString().isBlank()){
                    Toast.makeText(this,R.string.recipe_must_have_title,Toast.LENGTH_SHORT).show()
                    true
                }else{
                    val oldRecipe = intent.getParcelableExtra<Recipe>(RECIPE_EXTRA)
                    val recipe = Recipe(
                        etRecipeTitle.text.toString(),
                        etRecipeDescription.text.toString(),
                        etRecipeIngredients.text.toString(),
                        etRecipeInstructions.text.toString(),
                        created = if(oldRecipe?.created != null) oldRecipe.created else Date(),
                        recipeId = oldRecipe?.recipeId
                    )

                    if(recipe.recipeId == null) recipe.recipeId = viewModel.insertRecipe(recipe)
                    else viewModel.updateRecipe(recipe)

                    startViewRecipeActivity(recipe)

                    true
                }
            }
            android.R.id.home ->{
                val recipe = intent.getParcelableExtra<Recipe>(RECIPE_EXTRA)
                if(recipe?.recipeId != null)startViewRecipeActivity(recipe)
                else finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
