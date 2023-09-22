package com.example.influshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.influshop.api.ApiService
import com.example.influshop.databinding.ActivityDetailOrderBinding
import com.example.influshop.model.Influencer
import com.example.influshop.model.InfluencerKatalog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailOrderActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivityDetailOrderBinding
    private val apiService = ApiService()
    private lateinit var detailAdapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        // Initialize the RecyclerView and its adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        detailAdapter = DetailAdapter(ArrayList()) // Pass an empty list initially
        recyclerView.adapter = detailAdapter

        showData()
    }

    private fun showData() {
        apiService.detail()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    detailAdapter.updateData(result.data)
                },
                { error ->
                }
            )
    }
}
