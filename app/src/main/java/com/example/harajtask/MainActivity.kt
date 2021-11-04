package com.example.harajtask

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import model.CarPost
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import androidx.core.app.ActivityCompat
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import kotlinx.serialization.json.Json
import tools.DownloadImage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        askInternetPermission(this)
        configSupportBar()
        configPostList(loadListPost())
    }

    private fun askInternetPermission(act: Activity) {
        ActivityCompat.requestPermissions(
            act, arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
            ), 1
        )
    }

    fun configSupportBar(){
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_search)
        supportActionBar?.title=""
        supportActionBar?.setDisplayUseLogoEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    fun configPostList(posts: ArrayList<CarPost>){
        val arrayPosts = loadCacheImage(posts)
        val myListAdapter = CarPostListAdapter(this,posts, arrayPosts)

        postsList.adapter = myListAdapter

        postsList.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val carAsJson = Json.encodeToString(CarPost.serializer(), itemAtPos as CarPost)

            val showCarIntent = Intent(this, ShowPostActivity::class.java)
            showCarIntent.putExtra("selectedPost", carAsJson)

            startActivity(showCarIntent)
        }
    }


    fun loadListPost(): ArrayList<CarPost>{

        val jsonString: String
        val posts = ArrayList<CarPost>()
        try {
            jsonString = this.assets.open("data.json").bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(jsonString)

            for (i in 0..jsonArray.length() - 1) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)

                val car = jsonObject.getString("title")
                val user = jsonObject.getString("username")
                val uriImage = jsonObject.getString("thumbURL")
                val messagCount = jsonObject.getInt("commentCount")
                val location = jsonObject.getString("city")
                val dateNumber = jsonObject.getLong("date")
                val description = jsonObject.getString("body")

                posts.add(CarPost(uriImage,car,messagCount,location,dateNumber,user, description, ""))
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return posts
    }

    fun loadCacheImage(posts: java.util.ArrayList<CarPost>): ArrayList<Bitmap>{
        val images = ArrayList<Bitmap>()

        for (i in 0..posts.size - 1) {
            DownloadImage().downloadImage(posts.get(i).imageUri, true)?.let { images.add(it) }
        }

        return images
    }
}