package com.gm.newsnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gm.newsnet.conn.AppRepository
import com.gm.newsnet.databinding.ActivityMainBinding
import com.gm.newsnet.news.Article
import com.gm.newsnet.news.ArticlesAdapter
import com.gm.newsnet.news.category.CategoryFragment
import com.gm.newsnet.news.detail.DetailActivity
import com.gm.newsnet.utills.Resource
import com.gm.newsnet.utills.ViewModelFactory
import com.gm.newsnet.utills.constant.Status
import com.gm.newsnet.utills.ext.setHidden
import com.gm.newsnet.utills.ext.setVisible
import com.gm.newsnet.utills.listener.OnArticleListener
import com.gm.newsnet.utills.listener.OnCategoryListener
import com.gm.newsnet.utills.listener.OnScrollFullListener

class MainActivity : AppCompatActivity(), OnScrollFullListener, OnArticleListener,
    OnCategoryListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by lazy { ViewModelProvider(this, ViewModelFactory(application, AppRepository())).get(MainVM::class.java)}
    private val articlesAdapter by lazy { ArticlesAdapter( this, this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onClick()

        viewModel.getTopHeadlines()

        binding.lShimmer.duration= 800
        binding.lShimmer.startShimmerAnimation()

        observe()
        onScrollFull()
    }

    fun onClick(){
        binding.filter.setOnClickListener {
            CategoryFragment(
                listOf("General", "Business", "Entertainment", "Health", "Science", "Sports", "Technology"), this@MainActivity
            ).show(this.supportFragmentManager, "G")
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search= query.toString()
                viewModel.cat= ""
                loadData()

                return  true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onScrollFull() {
        super.onScrollFull()
        if(!binding.load.isVisible){
            viewModel.page++
            viewModel.getTopHeadlines()
        }
    }

    override fun onArticleSelected(data: Article) {
        super.onArticleSelected(data)
        Intent(this, DetailActivity::class.java).also{
            it.putExtra("article", data)
            startActivity(it)
        }

        this.overridePendingTransition(R.anim.slide_up_animation, R.anim.fade_exit_transition)
    }

    override fun onCategorySelected(data: String) {
        super.onCategorySelected(data)

        viewModel.cat= data.lowercase()
        viewModel.search= ""
        binding.search.setQuery("", false)
        loadData()
    }

    fun loadData(){
        hideKey()
        binding.message.setHidden()

        articlesAdapter.clearData()
        binding.lShimmer.setVisible()
        binding.lShimmer.duration= 800
        binding.lShimmer.startShimmerAnimation()
        viewModel.page= 1
        viewModel.getTopHeadlines()
    }

    fun hideKey(){
        val inputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        // on below line hiding our keyboard.
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }


    private fun observe(){
        viewModel.articles.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        if(binding.lShimmer.isVisible){
                            binding.lShimmer.stopShimmerAnimation()
                            binding.lShimmer.setHidden()
                        }

                        binding.load.setHidden()
                        response.data?.let { res ->
                            if(res.articles.size > 0){
                                if(binding.rvArticles.adapter == null){
                                    binding.rvArticles.apply {
                                        adapter= articlesAdapter
                                        layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                                    }
                                }
                                articlesAdapter.add(res.articles)
                                binding.rvArticles.post(Runnable { articlesAdapter.notifyItemInserted(articlesAdapter.itemCount)})
                            } else{
                                articlesAdapter.status= Status.CLEAR
                            }
                        }

                        if(articlesAdapter.itemCount == 0 && response.data!!.articles.size == 0){
                            binding.message.setVisible()
                        }
                    }

                    is Resource.Error -> {
                        if(binding.lShimmer.isVisible){
                            binding.lShimmer.stopShimmerAnimation()
                            binding.lShimmer.setHidden()
                        }

                        articlesAdapter.status= Status.CLEAR
                        binding.load.setHidden()
                        response.message?.let {}
                    }

                    is Resource.Loading -> { binding.load.setVisible()}
                }
            }
        })
    }
}