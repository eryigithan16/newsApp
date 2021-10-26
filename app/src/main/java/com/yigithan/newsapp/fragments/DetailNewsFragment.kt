package com.yigithan.newsapp.newsapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.yigithan.newsapp.R
import com.yigithan.newsapp.adapters.NewsAdapter
import com.yigithan.newsapp.newsapp.ui.NewsActivity
import com.yigithan.newsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_detail_news.*
import kotlinx.android.synthetic.main.item_article_recycler.*
import kotlinx.android.synthetic.main.item_article_recycler.view.*

class DetailNewsFragment : Fragment(R.layout.fragment_detail_news) {

    lateinit var viewModel : NewsViewModel
    val args: DetailNewsFragmentArgs by navArgs()
    private var favorite = false
    lateinit var link : String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setHasOptionsMenu(true)

        val article = args.article

        buttonToSource.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("articlesource", article)
            }
            findNavController().navigate(R.id.action_detailNewsFragment_to_sourceFragment2, bundle)
        }

        Glide.with(this).load(article.urlToImage).into(ivDetailImage)
        tvAuthorDetail.text = article.source?.name
        tvTitleDetail.text = article.title
        tvPublishedAtDetail.text = article.publishedAt
        tvContext.text = article.description
        link = article.url.toString()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
        setFavIcon(menu.findItem(R.id.nav_fav))
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_fav -> {
                favorite = !favorite
                setFavIcon(item)
                if (favorite){
                    viewModel.saveArticle(args.article)
                    view?.let { Snackbar.make(it,"Added Favorites",Snackbar.LENGTH_SHORT).show() }
                }
                else if(!favorite){
                    viewModel.deleteArticle(args.article)
                    view?.let { Snackbar.make(it,"Deleted from Favorites",Snackbar.LENGTH_SHORT).show() }
                }
            }

            R.id.nav_share -> {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, link)
                    type ="text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavIcon(menuItem: MenuItem){
        val id = if (favorite) R.drawable.ic_favorite;
        else R.drawable.ic_favorite_empty;
        menuItem.icon = context?.let { ContextCompat.getDrawable(it, id) }
    }
}