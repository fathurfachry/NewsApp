package com.farrell.muslimpedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.farrell.muslimpedia.R
import com.farrell.muslimpedia.adapter.NewsAdapter
import com.farrell.muslimpedia.data.repository.NewsRepository
import com.farrell.muslimpedia.databinding.FragmentAlJazeraBinding
import com.farrell.muslimpedia.databinding.FragmentWarningBinding
import com.farrell.muslimpedia.ui.NewsViewModel
import com.farrell.muslimpedia.utils.NewsViewModelFactory

class AlJazeraFragment : Fragment() {
    lateinit var binding : FragmentAlJazeraBinding
    private val commonViewModel : NewsViewModel by viewModels{
        NewsViewModelFactory(NewsRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlJazeraBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mAdapter = NewsAdapter()

        if (commonViewModel.alJazeeraNews.value == null){
            commonViewModel.getAlJazeraNews()
        }

        commonViewModel.getAlJazeraNews()
        commonViewModel.alJazeeraNews.observe(viewLifecycleOwner){ dataNews ->
            mAdapter.setData(dataNews.articles)

            binding.rvJazeeraNews.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        commonViewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading){
            binding.loadingView.root.visibility = View.VISIBLE
        }else{
            binding.loadingView.root.visibility = View.GONE
        }
    }
}