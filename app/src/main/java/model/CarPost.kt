package model

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.LocalDate
import kotlinx.serialization.serializer as serializer1


@kotlinx.serialization.Serializable
data class CarPost (
    var imageUri:String,
    var carName: String,
    var numberMessage: Int,
    var location: String,
    var timePostLong: Long,
    var nameUser: String,
    var description: String,
    var contact: String
){
    fun getTimeFormat():String{

        //posts in feb
        val timePost = Calendar.getInstance()
        timePost.time.time = timePostLong

        val pattern = "yyyy/MM/dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateStr = simpleDateFormat.format(timePost.time)

        return "Since " + dateStr
    }

    fun getTimeAllFormat():String{

        //posts in feb
        val timePost = Calendar.getInstance()
        timePost.time.time = timePostLong

        val pattern = "yyyy/MM/dd hh:mm aaa"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateStr = simpleDateFormat.format(timePost.time)

        return dateStr
    }
}

