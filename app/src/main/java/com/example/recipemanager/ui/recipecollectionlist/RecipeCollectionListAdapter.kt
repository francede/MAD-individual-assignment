package com.example.recipemanager.ui.recipecollectionlist

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.RecipeCollection
import kotlinx.android.synthetic.main.item_collection.view.*

class RecipeCollectionListAdapter(private val collections: List<RecipeCollection>,
                                  private val context: Context,
                                  fragment: Fragment
) : RecyclerView.Adapter<RecipeCollectionListAdapter.ViewHolder>(){

    private val viewModel = ViewModelProvider(fragment).get(RecipeCollectionListViewModel::class.java)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(collection: RecipeCollection) {
            itemView.tvCollectionTitle.text = collection.title

            itemView.ivCollectionMenu.setImageResource(R.drawable.ic_more_vert_white_24dp)

            itemView.ivCollectionMenu.setOnClickListener{
                val popup = PopupMenu(context, itemView.ivCollectionMenu)
                popup.inflate(R.menu.collection_item_context_menu)

                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.collection_item_rename -> {
                            //TODO
                            true
                        }
                        R.id.collection_item_delete -> {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("Confirm delete")
                            builder.setMessage("Are you sure you want to delete " + collection.title + "?")
                            builder.setPositiveButton("Delete"){ _, _ ->
                                viewModel.deleteCollection(collection)
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collections[position])
    }

    override fun getItemCount(): Int {
        return collections.size
    }
}