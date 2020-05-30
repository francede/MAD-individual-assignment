package com.example.recipemanager.ui.recipes

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.EditRecipeActivity
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeAdapter(private val recipes: List<Recipe>,
                    private val context: Context,
                    private val fragment: Fragment
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){

    private val viewModel = ViewModelProvider(fragment).get(RecipesViewModel::class.java)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: Recipe) {
            itemView.tvRecipeTitle.text = recipe.title

            itemView.ivRecipeMenu.setImageResource(R.drawable.ic_more_vert_white_24dp)

            itemView.ivRecipeMenu.setOnClickListener{
                val popup = PopupMenu(context, itemView.ivRecipeMenu)
                popup.inflate(R.menu.recipe_item_context_menu)

                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.recipe_item_add -> {
                            Toast.makeText(context,"Add recipe", Toast.LENGTH_SHORT).show()
                            //TODO
                            true
                        }
                        R.id.recipe_item_edit -> {
                            val intent = Intent(context, EditRecipeActivity::class.java)
                            intent.putExtra(EditRecipeActivity.RECIPE_EXTRA, recipe)
                            fragment.startActivityForResult(intent, EditRecipeActivity.EDIT_RECIPE_ACTIVITY_REQUEST_CODE)
                            true
                        }
                        R.id.recipe_item_delete -> {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("Confirm delete")
                            builder.setMessage("Are you sure you want to delete " + recipe.title + "?")
                            builder.setPositiveButton("Delete"){ _, _ ->
                                viewModel.deleteRecipe(recipe)
                            }
                            builder.setNegativeButton("Cancel"){ dialog, _ ->
                                dialog.dismiss()
                            }
                            builder.show()
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}