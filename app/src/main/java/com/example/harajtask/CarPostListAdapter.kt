package com.example.harajtask

import android.app.Activity
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.*
import model.CarPost
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import java.net.HttpURLConnection
import java.net.URLConnection
import java.net.URL
import java.io.InputStream
import java.io.IOException
import android.graphics.BitmapFactory
import java.util.*

class CarPostListAdapter(private val context: Activity, private val posts: ArrayList<CarPost>,
                         private val imagesCache: ArrayList<Bitmap>)
    : ArrayAdapter<CarPost>(context, R.layout.custom_item, posts) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_item, null, true)

        val carImage = rowView.findViewById(R.id.icon) as ImageView
        val location = rowView.findViewById(R.id.locationTxt) as TextView
        val carName = rowView.findViewById(R.id.carNameTxt) as TextView
        val timePost = rowView.findViewById(R.id.timePostTxt) as TextView
        val numberMessage = rowView.findViewById(R.id.numberMessagesTxt) as TextView
        val userName = rowView.findViewById(R.id.userNameTxt) as TextView
        val numberMessageBox = rowView.findViewById(R.id.numberMessagesLayout) as LinearLayout

        val image = imagesCache.get(position)
        carImage.setImageBitmap(image)

        location.text = posts.get(position).location
        carName.text = posts.get(position).carName
        timePost.text = posts.get(position).getTimeFormat()
        userName.text = posts.get(position).nameUser

        if(posts.get(position).numberMessage > 0) {
            numberMessageBox.visibility = View.VISIBLE;
            numberMessage.text = posts.get(position).numberMessage.toString()
        }

        return rowView
    }

}