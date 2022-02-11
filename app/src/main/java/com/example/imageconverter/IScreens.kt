package com.example.imageconverter

import android.os.Bundle
import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun convertorScreen(bundle: Bundle?): Screen
    fun imageListScreen(): Screen
}