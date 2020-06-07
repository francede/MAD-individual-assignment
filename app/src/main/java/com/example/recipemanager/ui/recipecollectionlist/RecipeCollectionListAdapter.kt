package com.example.recipemanager.ui.recipecollectionlist

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
import com.example.recipemanager.R
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.ui.COLLECTION_EXTRA
import com.example.recipemanager.ui.recipecollection.ViewCollectionActivity
import kotlinx.android.synthetic.main.item_collection.view.*
import kotlinx.android.synthetic.main.popup_rename_collection.view.*
import java.util.*

class RecipeCollectionListAdapter(private val collections: List<RecipeCollection>,
                                  private val context: Context,
                                  fragment: Fragment
) : RecyclerView.Adapter<RecipeCollectionListAdapter.ViewHolder>(){

    private val viewModel = ViewModelProvider(fragment).get(RecipeCollectionListViewModel::class.java)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(collection: RecipeCollection) {
            itemView.tvCollectionTitle.text = collection.title

            itemView.ivCollectionMenu.setImageResource(R.drawable.ic_more_vert_white_24dp)

            itemView.setOnClickListener{
                val intent = Intent(context, ViewCollectionActivity::class.java)
                intent.putExtra(COLLECTION_EXTRA, collection)
                context.startActivity(intent)
            }


            itemView.ivCollectionMenu.setOnClickListener{
                val popup = PopupMenu(context, itemView.ivCollectionMenu)
                popup.inflate(R.menu.collection_item_context_menu)

                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.collection_item_rename -> {
                            val popup = AlertDialog.Builder(context)
                            popup.setTitle(context.getString(R.string.rename_collection))

                            val popupView = LayoutInflater.from(context).inflate(R.layout.popup_rename_collection,null)
                            popupView.etCollectionName.setText(collection.title)

                            popup.setView(popupView)

                            popup.setPositiveButton(context.getString(R.string.rename)){ _, _ ->
                                collection.title = popupView.etCollectionName.text.toString()
                                viewModel.updateCollection(collection)
                                Toast.makeText(context, context.getString(R.string.saved_item, collection.title), Toast.LENGTH_SHORT).show()
                            }

                            popup.setNegativeButton(context.getString(R.string.cancel)){ dialog, _ ->
                                dialog.dismiss()
                            }

                            popup.show()

                            true
                        }
                        R.id.collection_item_delete -> {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle(context.getString(R.string.confirm_delete))
                            builder.setMessage(context.getString(R.string.sure_to_delete, collection.title))
                            builder.setPositiveButton(context.getString(R.string.delete)){ _, _ ->
                                viewModel.deleteCollection(collection)
                                Toast.makeText(context, context.getString(R.string.deleted_item, collection.title), Toast.LENGTH_SHORT).show()
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