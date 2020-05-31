package com.example.recipemanager.ui.recipes


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import kotlinx.android.synthetic.main.fragment_recipe.*

class RecipesFragment : Fragment() {

    private lateinit var viewModel: RecipesViewModel
    private val recipes = arrayListOf<Recipe>()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recipeAdapter = RecipeAdapter(recipes, context!!, this)
        viewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
        initViews()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                EditRecipeActivity.EDIT_RECIPE_ACTIVITY_REQUEST_CODE -> {
                    val recipe = data?.getParcelableExtra<Recipe>(EditRecipeActivity.RECIPE_EXTRA)
                        ?: return
                    if(recipe?.recipeId == null){
                        //insert
                        viewModel.insertRecipe(recipe)
                    }else{
                        //update
                        viewModel.updateRecipe(recipe)
                    }
                    recipeAdapter.notifyDataSetChanged()
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initViews(){
        rvRecipes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvRecipes.adapter = recipeAdapter
        rvRecipes.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun observeViewModel(){
        viewModel.recipes.observe(viewLifecycleOwner, Observer {recipes ->
            this.recipes.clear()
            this.recipes.addAll(recipes)
            recipeAdapter.notifyDataSetChanged()
        })
    }
}
