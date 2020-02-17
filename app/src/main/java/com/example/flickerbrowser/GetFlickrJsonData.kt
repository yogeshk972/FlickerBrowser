package com.example.flickerbrowser

import android.os.AsyncTask
import org.json.JSONObject

interface AfterParsed{
    fun onParsingComplete(photoList : ArrayList<Photo>)
    fun onError(e:java.lang.Exception)
}

class GetFlickrJsonData(val listener: AfterParsed): AsyncTask<String,Void,ArrayList<Photo>>() {

    override fun onPostExecute(result: ArrayList<Photo>) {
        listener.onParsingComplete(result)
    }

    override fun doInBackground(vararg params: String): ArrayList<Photo> {
        val photoList = ArrayList<Photo>()

        try{
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")

            for( i in 0 until itemsArray.length() ){
                val photo = itemsArray.getJSONObject(i)
                val title= photo.getString("title")
                val author= photo.getString("author")
                val authorId= photo.getString("author_id")
                val tags= photo.getString("tags")

                val link= photo.getJSONObject("media").getString("m")
                val image = link.replace("_m.jpg","_b.jpg",false)

                photoList.add(Photo(title,author,authorId,link,tags,image ))
            }

        }catch(e:Exception) {
            e.printStackTrace()
            cancel(true)
            listener.onError(e)
        }

        return photoList
    }
}
