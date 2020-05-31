package com.example.recipemanager.ui.recipecollection


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
import com.example.recipemanager.model.RecipeCollection
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : Fragment() {

    private lateinit var viewModel: RecipeCollectionViewModel
    private val collections = arrayListOf<RecipeCollection>()
    private lateinit var collectionAdapter: RecipeCollectionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectionAdapter = RecipeCollectionAdapter(collections, context!!, this)
        viewModel = ViewModelProvider(this).get(RecipeCollectionViewModel::class.java)
        initViews()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                CreateCollectionActivity.CREATE_COLLECTION_REQUEST_CODE -> {
                    val collection = data?.getParcelableExtra<RecipeCollection>(
                        CreateCollectionActivity.COLLECTION_EXTRA)
                        ?: return
                    viewModel.insertCollection(collection)
                    collectionAdapter.notifyDataSetChanged()
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initViews(){
        rvCollections.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvCollections.adapter = collectionAdapter
        rvCollections.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun observeViewModel(){
        viewModel.collections.observe(viewLifecycleOwner, Observer {collections ->
            this.collections.clear()
            this.collections.addAll(collections)
            collectionAdapter.notifyDataSetChanged()
        })
    }
}
