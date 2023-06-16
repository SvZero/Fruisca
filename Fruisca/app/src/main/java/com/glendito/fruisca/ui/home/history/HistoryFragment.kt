package com.glendito.fruisca.ui.home.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.glendito.fruisca.app.App
import com.glendito.fruisca.databinding.FragmentHistoryBinding
import com.glendito.fruisca.ui.settings.SettingsActivity
import javax.inject.Inject

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel

    @Inject
    lateinit var viewModelFactory: HistoryViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]

        adapter = HistoryAdapter()
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        viewModel.getAll()
        viewModel.fruits.observe(viewLifecycleOwner) { fruits ->
            binding.rvHistory.removeAllViews()
            adapter.setFruits(fruits)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}