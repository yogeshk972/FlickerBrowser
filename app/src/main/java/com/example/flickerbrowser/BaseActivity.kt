package com.example.flickerbrowser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

const val PHOTO_TRANSFER = "PHOTO_TRANSFER"
const val FLICKR_QUERY = "FLICKR_QUERY"

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {

    fun activateToolbar(enableHome : Boolean){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}