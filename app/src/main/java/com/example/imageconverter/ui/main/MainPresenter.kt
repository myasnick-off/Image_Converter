package com.example.imageconverter.ui.main

import com.example.imageconverter.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(val router: Router, val screens: IScreens) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.convertorScreen(null))
    }

    fun backPressed() {
        router.exit()
    }
}