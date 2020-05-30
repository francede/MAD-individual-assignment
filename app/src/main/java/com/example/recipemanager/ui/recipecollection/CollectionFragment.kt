package com.example.recipemanager.ui.recipecollection


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.customview.widget.FocusStrategy
import com.example.recipemanager.R
import com.example.recipemanager.RecipeDatabase
import com.example.recipemanager.model.RecipeCollection
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : Fragment() {

    private lateinit var viewModel: RecipeCollectionViewModel
    private val collections = arrayListOf<RecipeCollection>()
    private lateinit var collectionAdapter: CollectionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}
