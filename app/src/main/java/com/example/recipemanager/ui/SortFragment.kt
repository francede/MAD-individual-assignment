package com.example.recipemanager.ui

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.recipemanager.R
import kotlinx.android.synthetic.main.framgent_sort.*


class SortFragment(private val cb: OnClickCallback) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.framgent_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        tvSortTitle.setOnClickListener {
            cb.callback(SortOption.TITLE)
        }
        tvSortLastEdited.setOnClickListener{
            cb.callback(SortOption.LAST_UPDATED)
        }
        tvSortCreated.setOnClickListener{
            cb.callback(SortOption.CREATED)
        }
    }

    interface OnClickCallback{
        fun callback(option: SortOption)
    }

    enum class SortOption{
        TITLE, CREATED, LAST_UPDATED
    }
}