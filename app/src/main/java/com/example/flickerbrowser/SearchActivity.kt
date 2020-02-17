package com.example.flickerbrowser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activateToolbar(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate( R.menu.menu_search,menu )

        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as android.widget.SearchView

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)

        searchView.isIconified= false
        searchView.requestFocusFromTouch()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

             //   Toast.makeText(baseContext, "search for : $query ", Toast.LENGTH_LONG).show()
                val intent = Intent(baseContext,MainActivity::class.java)
                intent.putExtra(FLICKR_QUERY,addCommas(query) )
                startActivity(intent)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }
        })

        searchView.setOnCloseListener {
            finish()
            false
        }


        return true
    }

    fun addCommas( str:  String? ) :String {
        if(str==null || str.isEmpty() ) return ""
        var res = ""
        for(ch in str){
            if(ch==' ') res+=","
            else res+=ch
        }
        return res
    }
}
