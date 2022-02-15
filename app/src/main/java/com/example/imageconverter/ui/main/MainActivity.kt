package com.example.imageconverter.ui.main

import android.os.Bundle
import com.example.imageconverter.*
import com.example.imageconverter.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator = AppNavigator(this, R.id.main_container)
    private val presenter by moxyPresenter { MainPresenter(App.appInstance.router, AndroidScreens()) }
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.appInstance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.appInstance.navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backPressed()
    }
}