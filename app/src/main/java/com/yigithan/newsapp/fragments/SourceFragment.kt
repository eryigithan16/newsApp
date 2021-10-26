package com.yigithan.newsapp.newsapp.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.yigithan.newsapp.R
import com.yigithan.newsapp.newsapp.ui.NewsActivity
import com.yigithan.newsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_detail_news.*
import kotlinx.android.synthetic.main.fragment_source.*

class SourceFragment : Fragment(R.layout.fragment_source) {

    lateinit var viewModel : NewsViewModel
    val args: SourceFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        //(activity as AppCompatActivity).setSupportActionBar(tbSource)
        val article = args.articlesource
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url.toString())
        }
    }
}