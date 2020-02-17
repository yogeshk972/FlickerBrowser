package com.example.flickerbrowser

import android.os.Parcel
import android.os.Parcelable

class Photo(val title:String,val author:String,val authorID:String,val link: String,val tags:String,val image:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorID='$authorID', link='$link', tags='$tags', image='$image')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(authorID)
        parcel.writeString(link)
        parcel.writeString(tags)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

}