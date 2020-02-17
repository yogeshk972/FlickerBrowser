package com.example.flickerbrowser

import android.os.AsyncTask
import android.util.Log
import java.net.URL

enum class DownloadingStatus{
    OK, IDLE, ERROR
}

interface AfterDownload{
    fun onDownloadComplete(data:String, downloadingStatus:DownloadingStatus)
}

class GetRawData(private val listener : AfterDownload): AsyncTask<String,Void,String>() {
    private var STATUS = DownloadingStatus.IDLE
    private val TAG = "Get RAW data"

    override fun onPostExecute(result: String) {
        listener.onDownloadComplete(result,STATUS)
    }

    override fun doInBackground(vararg params: String?): String {
        if(params[0]==null){
            STATUS = DownloadingStatus.ERROR
            return ""
        }

        return try {
            STATUS = DownloadingStatus.OK
            URL(params[0]).readText()
        }catch(e:Exception){
            STATUS = DownloadingStatus.ERROR
            Log.d(TAG," ERROR : ${e.message}" )
            ""
        }
    }

}