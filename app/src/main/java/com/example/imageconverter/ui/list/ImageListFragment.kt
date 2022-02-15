package com.example.imageconverter.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.example.imageconverter.AndroidScreens
import com.example.imageconverter.App
import com.example.imageconverter.BackButtonListener
import com.example.imageconverter.databinding.FragmentImageListBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class ImageListFragment: MvpAppCompatFragment(), ImageListView, BackButtonListener {

    private val presenter by moxyPresenter {
        ImageListPresenter(App.appInstance.router, AndroidScreens())
    }
    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun backPressed(): Boolean {
        return presenter.backPressed()
    }

    override fun init() {
        // получаем список файлов в папке assets в подпапке FILE_RELATIVE_PATH
        val files = requireContext().assets.list(FILE_RELATIVE_PATH)
        // для каждого имени файла создаем TextView, форматируем ее, вешаем Listener и добавляем во фрагмент
        files?.let {
            for (file in files) {
                val fileNameText = TextView(context).apply {
                    LinearLayout.LayoutParams.MATCH_PARENT
                    LinearLayout.LayoutParams.WRAP_CONTENT
                    textSize = 20f
                    setPadding(20)
                    text = file
                    setOnClickListener {
                        // по нажатию на TextView запускаем фрагмент конвертера и передаем туда имя выбранного файла
                        presenter.runConverter("${FILE_RELATIVE_PATH}$file")
                    }
                }
                binding.root.addView(fileNameText)
            }
        }
    }

    companion object {
        const val ARG_FILE_NAME = "file_name"
        const val FILE_RELATIVE_PATH = "img/"

        fun newInstance() = ImageListFragment()
    }
}