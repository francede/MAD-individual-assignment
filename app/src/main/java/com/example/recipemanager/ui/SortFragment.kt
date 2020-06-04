package com.example.recipemanager.ui

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.recipemanager.R



class SortFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.framgent_sort, container, false)
    }
/*
    companion object {

        fun newInstance(): AddPhotoBottomDialogFragment {
            return AddPhotoBottomDialogFragment()
        }
    }*/
}