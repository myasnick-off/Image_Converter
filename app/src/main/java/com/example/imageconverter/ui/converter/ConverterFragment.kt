package com.example.imageconverter.ui.converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imageconverter.*
import com.example.imageconverter.databinding.FragmentConverterBinding
import com.example.imageconverter.repository.RepositoryImpl
import com.example.imageconverter.ui.list.ImageListFragment.Companion.ARG_FILE_NAME
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.io.IOException
import java.io.InputStream

class ConverterFragment : MvpAppCompatFragment(), ConverterView, BackButtonListener {

    private val presenter by moxyPresenter {
        ConverterPresenter(RepositoryImpl(), App.appInstance.router, AndroidScreens())
    }
    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private var jpgImage: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.doDispose()
        _binding = null
    }

    // инициализируем экран конвертера
    override fun init() {
        // если в аргументах поступило имя файла для конвертации, отображаем его на экране
        arguments?.let {
            val fileName = it.getString(ARG_FILE_NAME)
            presenter.openImage(fileName!!, requireContext())
        }

        binding.openJpgButton.setOnClickListener {
            // при нажатии на кнопку запускаем экран со списком файлов
            presenter.runImageList()
        }

        binding.convertButton.setOnClickListener {
            // при нажатии на кнопку, если картинка открыта, запускаем процесс конвертации
            if (jpgImage != null) {
                presenter.convertImage(jpgImage!!, requireContext())
            } else {
                Snackbar.make(binding.root, getString(R.string.choose_file), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    // выводим на экран картинку выбранную для конвертации
    override fun showImageToConvert(bitmap: Bitmap?) {
        jpgImage = bitmap
        jpgImage?.let {
            binding.pickedImageView.setImageBitmap(it)
        }
    }

    // выводим результат конвертации на экран
    override fun showResult(bitmap: Bitmap?) {
        if (bitmap != null) {
            binding.convertedImageView.setImageBitmap(bitmap)
        } else {
            Snackbar.make(binding.root, getString(R.string.conversion_failure), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showOpeningProgress() {
        binding.openProgressBar.visibility = View.VISIBLE
    }

    override fun hideOpeningProgress() {
        binding.openProgressBar.visibility = View.GONE
    }

    override fun showConvertingProgress() {
        binding.convertProgressBar.visibility = View.VISIBLE
    }

    override fun hideConvertingProgress() {
        binding.convertProgressBar.visibility = View.GONE
    }

    override fun backPressed(): Boolean {
        return presenter.backPressed()
    }

    companion object {
        fun newInstance(bundle: Bundle?):  ConverterFragment {
            return ConverterFragment().apply {
                arguments = bundle
            }
        }
    }
}