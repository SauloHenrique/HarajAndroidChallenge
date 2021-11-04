package tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import model.CarPost
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList

class DownloadImage {
    fun downloadImage(url: String?, reduceQuality:Boolean): Bitmap? {
        var bitmap: Bitmap? = null
        var stream: InputStream? = null
        val bmOptions = BitmapFactory.Options()
        bmOptions.inSampleSize = 1
        try {
            stream = getHttpConnection(url)
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions)

            //reduce quality to list
            if (reduceQuality && bitmap != null) {
                val stream2 = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream2)
                val byteArray = stream2.toByteArray()
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }

            stream!!.close()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return bitmap
    }

    fun getHttpConnection(urlString: String?): InputStream? {
        var stream: InputStream? = null
        val url = URL(urlString)
        val connection = url.openConnection()
        try {
            val httpConnection = connection as HttpURLConnection
            httpConnection.requestMethod = "GET"
            httpConnection.connect()
            if (httpConnection.responseCode == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.inputStream
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return stream
    }
}