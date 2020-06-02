package com.example.recipemanager.ui.recipecollectionlist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.ui.COLLECTION_EXTRA
import com.example.recipemanager.ui.recipecollection.ViewCollectionActivity
import com.example.recipemanager.ui.recipecollection.ViewCollectionAdapter
import kotlinx.android.synthetic.main.item_collection.view.*
import kotlinx.android.synthetic.main.popup_rename_collection.view.*

class RecipeCollectionListAdapter(private val collections: List<RecipeCollection>,
                                  private val context: Context,
                                  fragment: Fragment
) : RecyclerView.Adapter<RecipeCollectionListAdapter.ViewHolder>(){

    private val viewModel = ViewModelProvider(fragment).get(RecipeCollectionListViewModel::class.java)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(collection: RecipeCollection) {
            itemView.tvCollectionTitle.text = collection.title
            /*itemView.etCollectionTitle.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if(!hasFocus){
                        itemView.tvCollectionTitle.visibility = View.VISIBLE
                        itemView.etCollectionTitle.visibility = View.INVISIBLE

                        collection.title = itemView.etCollectionTitle.text.toString()
                        viewModel.updateCollection(collection)
                        itemView.tvCollectionTitle.text = collection.title
                    }
                }

                itemView.etCollectionTitle.setText(itemView.tvCollectionTitle.text.toString())
                            itemView.tvCollectionTitle.visibility = View.INVISIBLE
                            itemView.etCollectionTitle.visibility = View.VISIBLE
                            itemView.etCollectionTitle.requestFocus()

             */

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
                            popup.setTitle("Rename collection")

                            val popupView = LayoutInflater.from(context).inflate(R.layout.popup_rename_collection,null)
                            popupView.etCollectionName.setText(collection.title)

                            popup.setView(popupView)

                            popup.setPositiveButton("Rename"){ _, _ ->
                                collection.title = popupView.etCollectionName.text.toString()
                                viewModel.updateCollection(collection)
                            }

                            popup.setNegativeButton("Cancel"){ dialog, _ ->
                                dialog.dismiss()
                            }

                            popup.show()

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