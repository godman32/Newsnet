package com.gm.newsnet.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.gm.newsnet.R
import com.gm.newsnet.databinding.ItemArticleBinding
import com.gm.newsnet.utills.constant.Status
import com.gm.newsnet.utills.constant.TimeFormat
import com.gm.newsnet.utills.ext.format
import com.gm.newsnet.utills.ext.setHidden
import com.gm.newsnet.utills.ext.setVisible
import com.gm.newsnet.utills.ext.toDate
import com.gm.newsnet.utills.listener.OnArticleListener
import com.gm.newsnet.utills.listener.OnScrollFullListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Created by @godman on 04/07/23.
 */

class ArticlesAdapter(
    private val onScrollFullListener: OnScrollFullListener,
    private val onArticleListener: OnArticleListener
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    private var articles = ArrayList<Article>()

    var status= Status.FREE

    fun add(articles: List<Article>) {
        this.articles.addAll(articles)
        status= Status.FREE
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData(){
        articles.clear()
        notifyDataSetChanged()
        status= Status.FREE
    }

    class ViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        val lp = holder.itemView.layoutParams
        if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
            if (holder.layoutPosition % 5 == 0 || holder.layoutPosition== 0) {
                lp.isFullSpan = true
            } else{
                lp.isFullSpan = false
            }
        }

        super.onViewAttachedToWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text= articles[position].title

        if(articles[position].description.isNullOrEmpty()){
            holder.binding.desc.setHidden()
        } else{
            holder.binding.desc.setVisible()
            holder.binding.desc.text= articles[position].description
        }
        holder.binding.author.text= articles[position].source!!.name

        try {
            holder.binding.date.text= articles[position].publishedAt.toString().toDate(TimeFormat.SYSTEM).format(TimeFormat.EEE_DD_MMM_HH_MM)
        } catch (e:Exception){
            holder.binding.date.text= ""
        }

        CoroutineScope(Dispatchers.IO).launch {
            holder.binding.image.load(articles[position].urlToImage){
                listener(
                    onSuccess = { _, _ ->
                        holder.binding.load.setHidden()
                        holder.binding.image.scaleType= ImageView.ScaleType.CENTER_CROP
                    }, onError = { _, _ ->
                        holder.binding.image.setImageResource(R.mipmap.newsnet)
                        holder.binding.image.scaleType= ImageView.ScaleType.FIT_CENTER
                        holder.binding.load.setHidden()
                    }
                )
            }
        }


        if(position > itemCount - 8 && status == Status.FREE && itemCount > 0){
            status= Status.LOAD
            onScrollFullListener.onScrollFull()
        }

        holder.binding.content.setOnClickListener {
            onArticleListener.onArticleSelected(articles[position])
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

}