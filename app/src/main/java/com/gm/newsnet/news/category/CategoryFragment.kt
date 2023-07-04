package com.gm.newsnet.news.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gm.newsnet.R
import com.gm.newsnet.databinding.FragmentCategoryBinding
import com.gm.newsnet.utills.listener.OnCategoryListener


class CategoryFragment(
    private val cat: List<String>,
    private val onCategoryListener: OnCategoryListener
) : DialogFragment(), OnCategoryListener {


    private val binding by lazy { FragmentCategoryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvGenres.apply {
            adapter = CategoryAdapter(cat, this@CategoryFragment)
        }
    }

    override fun onCategorySelected(data: String) {
        super.onCategorySelected(data)

        onCategoryListener.onCategorySelected(data)
        dismissAllowingStateLoss()
    }

    override fun getTheme(): Int {
        return R.style.AppTheme_FullScreenDialog
    }

}