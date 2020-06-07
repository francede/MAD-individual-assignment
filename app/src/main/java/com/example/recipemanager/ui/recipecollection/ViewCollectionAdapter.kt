package com.example.recipemanager.ui.recipecollection

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.Recipe
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.ui.EDIT_RECIPE_ACTIVITY_REQUEST_CODE
import com.example.recipemanager.ui.RECIPE_EXTRA
import com.example.recipemanager.ui.recipe.EditRecipeActivity
import com.example.recipemanager.ui.recipe.ViewRecipeActivity
import kotlinx.android.synthetic.main.item_recipe.view.*

class ViewCollectionAdapter(private val recipes: List<Recipe>,
                            private val activity: ViewCollectionActivity,
                            private val collection: RecipeCollection
) : RecyclerView.Adapter<ViewCollectionAdapter.ViewHolder>(){

    private val viewModel = ViewModelProvider(activity,
        ViewCollectionViewModelFactory(activity.application, collection.collectionId!!))
        .get(ViewCollectionViewModel::class.java)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: Recipe) {
            itemView.tvRecipeTitle.text = recipe.title

            itemView.setOnClickListener{
                val intent = Intent(activity, ViewRecipeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra(RECIPE_EXTRA, recipe)
                activity.startActivity(intent)
            }

            itemView.ivRecipeMenu.setImageResource(R.drawable.ic_more_vert_white_24dp)

            itemView.ivRecipeMenu.setOnClickListener{
                val popup = PopupMenu(activity, itemView.ivRecipeMenu)
                popup.inflate(R.menu.recipe_item_in_collection_menu)

                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.recipe_item_remove -> {
                            viewModel.deleteRecipeInCollection(recipe.recipeId!!, collection.collectionId!!)
                            Toast.makeText(activity, activity.getString(R.string.removed_item_from_item, recipe.title, collection.title), Toast.LENGTH_SHORT).show()
                            true
                        }
                        R.id.recipe_item_edit -> {
                            val intent = Intent(activity, EditRecipeActivity::class.java)
                            intent.putExtra(RECIPE_EXTRA, recipe)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            activity.startActivityForResult(intent, EDIT_RECIPE_ACTIVITY_REQUEST_CODE)
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