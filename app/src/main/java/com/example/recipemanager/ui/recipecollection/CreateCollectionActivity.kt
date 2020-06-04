package com.example.recipemanager.ui.recipecollection

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import com.example.recipemanager.R
import com.example.recipemanager.model.RecipeCollection
import com.example.recipemanager.ui.COLLECTION_EXTRA
import kotlinx.android.synthetic.main.activity_create_collection.*

class CreateCollectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_collection)

        initViews()
    }

    private fun initViews(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.create_collection)

        //Set watcher that listens when text changes (button should be disabled if edittext is empty)
        etCollectionTitle.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val clickable = !p0.toString().isBlank()
                btnCreateCollection.isClickable = clickable
                if(clickable){
                    btnCreateCollection.background = getDrawable(R.color.colorPrimaryRed)
                    btnCreateCollection.setTextColor(resources.getColor(R.color.colorTextLight))
                }else{
                    btnCreateCollection.background = getDrawable(R.color.colorPrimaryGrey)
                    btnCreateCollection.setTextColor(resources.getColor(R.color.colorTextDark))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {} //Ignore
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}//Ignore
        })

        btnCreateCollection.setOnClickListener{
            val collection = RecipeCollection(etCollectionTitle.text.toString())
            val resIntent = Intent()
            resIntent.putExtra(COLLECTION_EXTRA, collection)
            setResult(Activity.RESULT_OK, resIntent)
            finish()
        }

        btnCreateCollection.isClickable = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
