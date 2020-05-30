package com.example.recipemanager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.recipemanager.model.Recipe
import kotlinx.android.synthetic.main.activity_edit_recipe.*

class EditRecipeActivity : AppCompatActivity() {

    companion object{
        const val RECIPE_EXTRA = "RECIPE_EXTRA"
        const val EDIT_RECIPE_ACTIVITY_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        initViews()
    }

    private fun initViews(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.edit_recipe_activity_title)

        val recipe = intent.getParcelableExtra<Recipe>(RECIPE_EXTRA)
        if(recipe != null){
            etRecipeTitle.setText(recipe.title)
            etRecipeDescription.setText(recipe.description)
            etRecipeIngredients.setText(recipe.instructions)
            etRecipeInstructions.setText(recipe.instructions)
        }
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
                        intent.getParcelableExtra<Recipe>(RECIPE_EXTRA).recipeId
                    )

                    val resIntent = Intent()
                    resIntent.putExtra(RECIPE_EXTRA, recipe)
                    setResult(Activity.RESULT_OK, resIntent)
                    finish()
                    true
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
