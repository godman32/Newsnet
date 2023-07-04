package com.gm.newsnet.news.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import coil.load
import com.gm.newsnet.R
import com.gm.newsnet.databinding.ActivityDetailBinding
import com.gm.newsnet.news.Article
import com.gm.newsnet.utills.constant.TimeFormat
import com.gm.newsnet.utills.ext.format
import com.gm.newsnet.utills.ext.setHidden
import com.gm.newsnet.utills.ext.toDate
import com.gm.newsnet.utills.ext.truncateExtra
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val article by lazy { intent.getSerializableExtra("article") as Article }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setView()
        onClick()

        cekLayout()
    }

    fun setView(){
        binding.title.text= article.title
        binding.author.text= article.source!!.name
        binding.date.text= article.publishedAt.toString().toDate(TimeFormat.SYSTEM).format(
            TimeFormat.MMM_DD_YYYY_HH_MM)
        binding.desc.text= article.description


        if(!article.content.isNullOrEmpty()){
            binding.content.text= article.content!!.truncateExtra()
        }


        CoroutineScope(Dispatchers.IO).launch {
            binding.image.load(article.urlToImage){
                listener(
                    onSuccess = { _, _ ->
                        binding.load.setHidden()
                        binding.image.scaleType= ImageView.ScaleType.CENTER_CROP
                    }, onError = { _, _ ->
                        binding.image.setImageResource(R.mipmap.newsnet)
                        binding.image.scaleType= ImageView.ScaleType.FIT_CENTER
                        binding.load.setHidden()
                    }
                )
            }
        }
    }

    fun onClick(){
        binding.back.setOnClickListener {
            finishAndRemoveTask()
        }

        binding.read.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(browserIntent)
        }
    }

    private fun cekLayout(){
        val params = binding.appBar.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        if((binding.scroll.getChildAt(0).getBottom() - binding.scroll.getHeight()) < 500){
            behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return false
                }
            })
        } else{
            behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return true
                }
            })
        }
    }
}