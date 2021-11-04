package com.example.harajtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_show_post.*
import kotlinx.serialization.json.Json
import model.CarPost
import tools.DownloadImage

class ShowPostActivity : AppCompatActivity() {
    var titlePost = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_post)

        configSupportBar()

        val jsonPost = intent.getStringExtra("selectedPost")

        if(!jsonPost!!.isEmpty()){
            fillWidgets(jsonPost)
        }else{
            finish()
        }
    }

    fun configSupportBar(){
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_share);
        supportActionBar?.setTitle("");

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        sendTitleShareSheet()

        return super.onOptionsItemSelected(item)
    }

    fun fillWidgets(jsonPost: String){
        val post = Json.decodeFromString(CarPost.serializer(), jsonPost)

        //fill elements

        //problem internt connection
        val imagem = DownloadImage().downloadImage(post.imageUri, false)
        if(imagem != null) {
            imagemCar2.setImageBitmap(imagem)
        }
        titlePost = post.carName
        carName2Txt.text = post.carName
        dataPost2Txt.text = post.getTimeAllFormat()
        location2Txt.text = post.location
        informationCar2.text = post.description

        //contact doesn't present in JSON...
    }

    //send to share sheet
    fun sendTitleShareSheet(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, titlePost)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}