package com.example.imageconverter.ui.converter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.imageconverter.IScreens
import com.example.imageconverter.repository.Repository
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class ConverterPresenter(
    private val repository: Repository,
    val router: Router,
    val screens: IScreens
) : MvpPresenter<ConverterView>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun openImage(fileName: String, context: Context) {
        viewState.showOpeningProgress()
        compositeDisposable.add(
        repository.loadImage(fileName, context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewState.showImageToConvert(it)
                    viewState.hideOpeningProgress()
                },
                {
                    Log.e("mylog", "${it.message}")
                }
            )
        )
    }

    fun convertImage(image: Bitmap, context: Context) {
        viewState.showConvertingProgress()
        compositeDisposable.add(
            repository.convertJpgToPng(image)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map {
                    repository.saveImage(it, context)
                    BitmapFactory.decodeByteArray(it, 0, it.size)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    null
                }
                .subscribe(
                    {
                        viewState.showResult(it)
                        viewState.hideConvertingProgress()
                    },
                    {
                        Log.e("mylog", "${it.message}")
                    }
                )
        )
    }

    fun runImageList() {
        router.navigateTo(screens.imageListScreen())
    }

    fun backPressed(): Boolean {
        router.exit()
        return false
    }

    fun doDispose() {
        compositeDisposable.dispose()
    }


}