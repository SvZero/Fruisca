package com.glendito.fruisca.ui.newsdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.glendito.fruisca.app.App
import com.glendito.fruisca.databinding.ActivityNewsDetailBinding
import javax.inject.Inject

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var viewModel: NewsDetailViewModel

    @Inject
    lateinit var viewModelFactory: NewsDetailViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsId = intent?.getIntExtra("newsId", -1) ?: -1
        if (newsId == -1) finish()
        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsDetailViewModel::class.java]
        viewModel.fetchDetail(newsId)
        viewModel.news.observe(this) { news ->
            news?.let {
                binding.txtTitle.text = news.title
                binding.txtContent.text = news.content
                Glide.with(this)
                    .load(news.image)
                    .into(binding.img)
            }
        }

        binding.btnBack.setOnClickListener { finish() }
    }
}