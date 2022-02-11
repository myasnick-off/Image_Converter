package com.example.imageconverter.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import io.reactivex.rxjava3.core.Single
import java.io.ByteArrayOutputStream

class RepositoryImpl: Repository {

    override fun loadImage(fileName: String, context: Context): Single<Bitmap> = Single.fromCallable {
        Thread.sleep(1000) // искуственно замедляем процесс
        val stream = context.assets.open(fileName)
        val bitmap = BitmapFactory.decodeStream(stream)
        stream.close()
        return@fromCallable bitmap
    }

    override fun convertJpgToPng(image: Bitmap): Single<ByteArray> = Single.fromCallable {
        Thread.sleep(1000) // искуственно замедляем процесс
        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        outputStream.close()
        return@fromCallable byteArray
    }

    override fun saveImage(byteArray: ByteArray, context: Context) {
        context.openFileOutput("my_png", Context.MODE_PRIVATE).use {
            it.write(byteArray) }

        val files = context.fileList()
        for (file in files!!) { Log.d("mylog", file) }
    }

}