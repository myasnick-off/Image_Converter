package com.example.imageconverter.repository

import android.content.Context
import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun loadImage(fileName: String, context: Context): Single<Bitmap>
    fun saveImage(byteArray: ByteArray, context: Context)
    fun convertJpgToPng(image: Bitmap): Single<ByteArray>
}