package com.example.flickerbrowser

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.android.synthetic.main.content_photo_details.*

class PhotoDetailsActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activateToolbar(true)

       // val photo= intent.getSerializableExtra(PHOTO_TRANSFER) as Photo
        val photo = intent.extras?.getParcelable<Photo>(PHOTO_TRANSFER)
        var author = photo?.author

        if(author != null){
           var foo= false
           var temp  = ""
           for(ch in author){
                if(ch=='"') foo=foo xor true
               else if(foo){
                    temp += ch
                }
            }
            if(temp.isNotEmpty()) author=temp
        }else author = ""

        photo_author.text = "Author : $author"
        photo_title.text = "Title : ${photo?.title}"
        photo_tags.text = "Tags : ${photo?.tags}"
        Picasso.get().load( photo?.link ).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(photo_image)
    }

}
