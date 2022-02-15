package com.example.imageconverter.ui.converter

import android.graphics.Bitmap
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ConverterView : MvpView {
    fun init()
    fun showResult(bitmap: Bitmap?)
    fun showImageToConvert(bitmap: Bitmap?)
    fun showOpeningProgress()
    fun hideOpeningProgress()
    fun showConvertingProgress()
    fun hideConvertingProgress()
}