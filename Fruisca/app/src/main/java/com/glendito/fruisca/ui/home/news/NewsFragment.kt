package com.glendito.fruisca.ui.home.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.glendito.fruisca.app.App
import com.glendito.fruisca.databinding.FragmentNewsBinding
import com.glendito.fruisca.ui.newsdetail.NewsDetailActivity
import javax.inject.Inject

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsAdapter { newsId ->
            startActivity(Intent(requireContext(), NewsDetailActivity::class.java).apply {
                putExtra("newsId", newsId)
            })
        }
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapter

        (requireActivity().application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        viewModel.fetchNews()
        viewModel.news.observe(viewLifecycleOwner) { news ->
            binding.rvNews.removeAllViews()
            adapter.setNews(news)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}