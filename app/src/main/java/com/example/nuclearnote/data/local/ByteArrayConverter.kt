package com.example.nuclearnote.data.local

import android.util.Base64
import androidx.room.TypeConverter

class ByteArrayConverter {

    @TypeConverter
    fun fromListByteArray(images: ArrayList<ByteArray>): String {
        val stringBuilder = StringBuilder()
        for (image in images) {
            stringBuilder.append(Base64.encodeToString(image, Base64.DEFAULT))
            stringBuilder.append(",")
        }
        return stringBuilder.toString()
    }

    @TypeConverter
    fun toListByteArray(imagesString: String): ArrayList<ByteArray> {
        val imageArray = imagesString.split(",")
        val images = mutableListOf<ByteArray>()
        for (imageString in imageArray) {
            val decodedImage = Base64.decode(imageString, Base64.DEFAULT)
            images.add(decodedImage)
        }
        return ArrayList(images)
    }

}