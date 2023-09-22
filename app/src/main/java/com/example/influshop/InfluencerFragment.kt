package com.example.influshop

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.influshop.api.ApiService
import com.example.influshop.databinding.FragmentInfluencerBinding
import com.example.influshop.model.Influencer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class InfluencerFragment : Fragment() {
    private lateinit var binding: FragmentInfluencerBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var influencerAdapter: InfluencerAdapter
    private val apiService = ApiService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfluencerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = binding.recyclerView
        influencerAdapter = InfluencerAdapter(ArrayList())
        // Set layout manager (e.g., GridLayoutManager)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // Set the adapter for the RecyclerView
        recyclerView.adapter = influencerAdapter

        // Fetch data from the API and display it
        apiService.influencer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ influencers ->
                // Update the adapter with the fetched data
                influencerAdapter.updateData(influencers.data)
            }, { error ->
                // Handle error
                showError(error.message)
            })
    }

    private fun showError(errorMessage: String?) {
        // Handle and display error message
    }
}
