package com.example.recipemanager.ui.recipelist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.ui.EDIT_RECIPE_ACTIVITY_REQUEST_CODE
import com.example.recipemanager.ui.RECIPE_EXTRA
import com.example.recipemanager.ui.addtocollection.AddToCollectionActivity
import com.example.recipemanager.ui.recipe.EditRecipeActivity
import com.example.recipemanager.ui.recipe.ViewRecipeActivity
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeListAdapter(private val recipes: List<Recipe>,
                        private val context: Context,
                        private val fragment: Fragment
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){

    private val viewModel = ViewModelProvider(fragment).get(RecipeListViewModel::class.java)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: Recipe) {
            itemView.tvRecipeTitle.text = recipe.title

            itemView.setOnClickListener{
                val intent = Intent(context, ViewRecipeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra(RECIPE_EXTRA, recipe)
                context.startActivity(intent)
            }

            itemView.ivRecipeMenu.setImageResource(R.drawable.ic_more_vert_white_24dp)

            itemView.ivRecipeMenu.setOnClickListener{
                val popup = PopupMenu(context, itemView.ivRecipeMenu)
                popup.inflate(R.menu.recipe_item_context_menu)

                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.recipe_item_add -> {
                            val intent = Intent(context, AddToCollectionActivity::class.java)
                            intent.putExtra(RECIPE_EXTRA, recipe)
                            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            fragment.startActivity(intent)
                            true
                        }
                        R.id.recipe_item_edit -> {

                            val intent = Intent(context, EditRecipeActivity::class.java)
                            intent.putExtra(RECIPE_EXTRA, recipe)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            fragment.startActivityForResult(intent, EDIT_RECIPE_ACTIVITY_REQUEST_CODE)

                            true
                        }
                        R.id.recipe_item_delete -> {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle(context.getString(R.string.confirm_delete))
                            builder.setMessage(context.getString(R.string.sure_to_delete, recipe.title))
                            builder.setPositiveButton(context.getString(R.string.delete)){ _, _ ->
                                viewModel.deleteRecipe(recipe)
                            }
                            builder.setNegativeButton(context.getString(R.string.cancel)){ dialog, _ ->
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