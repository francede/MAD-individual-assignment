package com.example.recipemanager.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.RecipeCollection
import kotlinx.android.synthetic.main.item_collection_select.view.*

//TODO
class SelectCollectionAdapter(private val collections: List<RecipeCollection>,
                              private val context: Context
) : RecyclerView.Adapter<SelectCollectionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(collection: RecipeCollection) {
            itemView.tvCollectionTitle.text = collection.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection_select, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collections[position])
    }

    override fun getItemCount(): Int {
        return collections.size
    }
}