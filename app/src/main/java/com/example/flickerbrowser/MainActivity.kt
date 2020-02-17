package com.example.flickerbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*

var oldUrl:String = ""
var oldPhotoList: ArrayList<Photo> = ArrayList()

class MainActivity : BaseActivity() ,AfterDownload,AfterParsed, RecylclerItemClickListener.OnRecyclerClickListener {
    val Tag = "vub"
    private val flickrRecylerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener( RecylclerItemClickListener(this,recycler_view,this ) )
        recycler_view.adapter = flickrRecylerViewAdapter

        val tags = if(intent.extras==null) "android" else intent.getStringExtra(FLICKR_QUERY)
        serchInFlickr(tags)
    }

    private fun serchInFlickr(tags: String){

        val newUrl = createUrl(tags,"en-us",false )
        if( oldUrl != newUrl ) {
            GetRawData(this).execute(newUrl)
            oldUrl=newUrl
        }

        val fabRefresh = findViewById<View>(R.id.fab)
        fabRefresh.setOnClickListener {
            GetRawData(this).execute(newUrl)
            recycler_view.scrollToPosition(0)
        }

    }

    override fun onItemClick(v: View, pos: Int) {
        Toast.makeText( this ,"item #$pos clicked",Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(v: View, pos: Int) {
        val intent = Intent( baseContext ,PhotoDetailsActivity::class.java)
        intent.putExtra(PHOTO_TRANSFER, flickrRecylerViewAdapter.getPhoto(pos) )
        startActivity(intent)
    }

    private fun createUrl(Tags:String, lang:String, matchAll:Boolean ):String{
        return Uri.parse("https://www.flickr.com/services/feeds/photos_public.gne").buildUpon().
               appendQueryParameter("tags",Tags).
               appendQueryParameter("tagmode", if(matchAll) "ALL" else "ANY" ).
               appendQueryParameter("lang",lang).
               appendQueryParameter("format","json").
               appendQueryParameter("nojsoncallback","1").toString()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity( Intent(this,SearchActivity::class.java) )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(data: String, downloadingStatus: DownloadingStatus) {

        if( downloadingStatus == DownloadingStatus.OK )
            GetFlickrJsonData(this).execute(data)
        else
            Log.d(Tag,"Download Failed")
    }

    override fun onParsingComplete(photoList: ArrayList<Photo>) {
        oldPhotoList = photoList
        flickrRecylerViewAdapter.loadNewData(photoList)
    }

    override fun onError(e: Exception) {
        Log.d(Tag,"Error Found : ${e.message}")
    }

    override fun onResume() {
        super.onResume()
        flickrRecylerViewAdapter.loadNewData(oldPhotoList)
    }

    override fun onPause() {
        super.onPause()
        Log.d("Sdfsf"," ${recycler_view.computeVerticalScrollOffset()}  , ${recycler_view.computeVerticalScrollExtent()} ")
    }
}
