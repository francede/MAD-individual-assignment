package com.example.recipemanager.ui.recipecollectionlist


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.R
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.ui.COLLECTION_EXTRA
import com.example.recipemanager.ui.CREATE_COLLECTION_REQUEST_CODE
import com.example.recipemanager.ui.MainActivity
import com.example.recipemanager.ui.SortFragment
import kotlinx.android.synthetic.main.fragment_collection.*

class RecipeCollectionListFragment : Fragment() {

    private lateinit var viewModel: RecipeCollectionListViewModel
    private val collections = arrayListOf<RecipeCollection>()
    private val allCollections = arrayListOf<RecipeCollection>()
    private lateinit var collectionAdapter: RecipeCollectionListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectionAdapter = RecipeCollectionListAdapter(collections, context!!, this)
        viewModel = ViewModelProvider(this).get(RecipeCollectionListViewModel::class.java)
        initViews()
        observeViewModel()

        sort(SortFragment.SortOption.TITLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                CREATE_COLLECTION_REQUEST_CODE -> {
                    val collection = data?.getParcelableExtra<RecipeCollection>(
                        COLLECTION_EXTRA)
                        ?: return
                    viewModel.insertCollection(collection)
                    collectionAdapter.notifyDataSetChanged()
                    Toast.makeText(context, getString(R.string.created_item, collection.title), Toast.LENGTH_SHORT).show()
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

        var scrollListener: RecyclerView.OnScrollListener? = null
        activity?.let{
            if(it is MainActivity) scrollListener = it.scrollListener
        }

        rvCollections.addOnScrollListener(scrollListener!!)
    }

    private fun observeViewModel(){
        viewModel.collections.observe(viewLifecycleOwner, Observer {collections ->
            this.collections.clear()
            this.collections.addAll(collections)
            this.allCollections.clear()
            this.allCollections.addAll(collections)
            collectionAdapter.notifyDataSetChanged()
        })
    }

    fun sort(sortOption: SortFragment.SortOption){
        when(sortOption){
            SortFragment.SortOption.TITLE -> collections.sortBy { it.title }
            SortFragment.SortOption.CREATED -> collections.sortBy { it.created }
            SortFragment.SortOption.LAST_UPDATED -> collections.sortBy { it.lastUpdated }
        }
        collectionAdapter.notifyDataSetChanged()
    }

    fun filter(filterText: String){
        collections.clear()
        if(filterText.isBlank()) {
            collections.addAll(allCollections)
        }else{
            val text = filterText.toLowerCase()
            allCollections.forEach{
                if(it.title.toLowerCase().contains(text)) collections.add(it)
                Toast.makeText(context, it.title.toLowerCase().contains(text).toString(), Toast.LENGTH_SHORT).show()
            }
        }
        collectionAdapter.notifyDataSetChanged()
    }
}
