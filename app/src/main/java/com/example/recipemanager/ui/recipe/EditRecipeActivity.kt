package com.example.recipemanager.ui.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.ui.RECIPE_EXTRA
import kotlinx.android.synthetic.main.activity_edit_recipe.*
import java.util.*

class EditRecipeActivity : AppCompatActivity() {

    private lateinit var viewModel: EditRecipeViewModel
    private var textChanged = false

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

        val textWatcher = object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                textChanged = true
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {} //Ignore
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}//Ignore
        }

        etRecipeTitle.addTextChangedListener(textWatcher)
        etRecipeDescription.addTextChangedListener(textWatcher)
        etRecipeIngredients.addTextChangedListener(textWatcher)
        etRecipeInstructions.addTextChangedListener(textWatcher)
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
                fun endActivity(){
                    val recipe = intent.getParcelableExtra<Recipe>(RECIPE_EXTRA)
                    if(recipe?.recipeId != null)startViewRecipeActivity(recipe)
                    else finish()
                }
                if(textChanged){
                    //Ask user if changes should be discarded
                    val popup = AlertDialog.Builder(this)
                    popup.setTitle(R.string.discard_changes)
                    popup.setMessage(R.string.sure_to_discard)
                    popup.setPositiveButton(R.string.discard){ _, _ ->
                        endActivity()
                    }
                    popup.setNegativeButton(R.string.cancel){ dialog, _ ->
                        dialog.dismiss()
                    }
                    popup.show()
                }else endActivity()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
