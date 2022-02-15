package com.example.imageconverter.ui.list

import android.os.Bundle
import com.example.imageconverter.IScreens
import com.example.imageconverter.ui.list.ImageListFragment.Companion.ARG_FILE_NAME
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class ImageListPresenter(val router: Router, val screens: IScreens) : MvpPresenter<ImageListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun runConverter(fileName: String) {
        val bundle = Bundle().apply {
            this.putString(ARG_FILE_NAME, fileName)
        }
        router.replaceScreen(screens.convertorScreen(bundle))
    }
}