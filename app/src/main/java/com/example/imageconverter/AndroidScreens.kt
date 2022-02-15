package com.example.imageconverter

import android.os.Bundle
import com.example.imageconverter.ui.converter.ConverterFragment
import com.example.imageconverter.ui.list.ImageListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {

    override fun convertorScreen(bundle: Bundle?) =
        FragmentScreen { ConverterFragment.newInstance(bundle) }

    override fun imageListScreen() = FragmentScreen { ImageListFragment.newInstance() }
}