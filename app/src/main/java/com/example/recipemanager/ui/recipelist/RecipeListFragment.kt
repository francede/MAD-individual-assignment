package com.example.recipemanager.ui.recipelist


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
import com.example.recipemanager.ui.EDIT_RECIPE_ACTIVITY_REQUEST_CODE
import com.example.recipemanager.ui.RECIPE_EXTRA
import com.example.recipemanager.ui.recipe.EditRecipeActivity
import kotlinx.android.synthetic.main.fragment_recipe.*

class RecipeListFragment : Fragment() {

    private lateinit var viewModel: RecipeListViewModel
    private val recipes = arrayListOf<Recipe>()
    private lateinit var recipeAdapter: RecipeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recipeAdapter = RecipeListAdapter(recipes, context!!, this)
        viewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        initViews()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                EDIT_RECIPE_ACTIVITY_REQUEST_CODE -> {
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
