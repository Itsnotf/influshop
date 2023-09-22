package com.example.influshop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.influshop.api.ApiService
import com.example.influshop.api.ResultMultiple
import com.example.influshop.databinding.ActivityKatalogBinding
import com.example.influshop.model.Influencer
import com.example.influshop.model.InfluencerKatalog
import com.example.influshop.model.Order
import com.example.influshop.model.Packet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class KatalogActivity : AppCompatActivity(), KatalogAdapter.OnTambahKeranjangClickListener {

    private val apiService = ApiService()
    private lateinit var viewBinder: ActivityKatalogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityKatalogBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        val influencerId = intent.getIntExtra("influencer_id", 0)
        val namaInfluencer = intent.getStringExtra("nama_influ")
        val bio = intent.getStringExtra("bio")
        val image = intent.getStringExtra("image")

        loadImage(image)

        viewBinder.namaInfluMain.text = namaInfluencer
        viewBinder.bioInflu.text = bio

        fetchInfluencerById(influencerId)
    }

    private fun loadImage(imageUrl: String?) {
        Glide.with(applicationContext)
            .load("https://itfest.cyborgitcenter.org/influencer_picture/${imageUrl}")
            .error(R.drawable.ic_launcher_background)
            .into(viewBinder.fotoInfluencerMain)
    }

    private fun fetchInfluencerById(influencerId: Int) {
        apiService.ambilPacketInfluencer(influencerId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<ResultMultiple<Packet>>() {
                override fun onSuccess(value: ResultMultiple<Packet>?) {
                    value?.data?.let { packets ->
                        val influencer = createInfluencerFromPackets(influencerId, packets)

                        val recyclerView = findViewById<RecyclerView>(R.id.rvKatalog)
                        recyclerView.layoutManager = LinearLayoutManager(this@KatalogActivity)

                        val influencerKatalog = ArrayList<InfluencerKatalog>()
                        influencerKatalog.add(InfluencerKatalog(influencer))

                        val adapter = KatalogAdapter(packets, this@KatalogActivity)
                        recyclerView.adapter = adapter
                    }
                }

                override fun onError(e: Throwable?) {
                    Log.e("FetchError", "Error: ${e?.message}")
                }
            })
    }

    private fun createInfluencerFromPackets(influencerId: Int, packets: List<Packet>): Influencer {
        return if (packets.isNotEmpty()) {
            val firstPacket = packets[0]
            Influencer(
                influencerId,
                "Sample Influencer",
                firstPacket.packet_name,
                "Sample Bio",
                "Sample TikTok",
                "Sample Instagram",
                "Sample Facebook",
                "2023-09-18",
                "2023-09-18"
            )
        } else {
            Influencer(
                influencerId,
                "Sample Influencer",
                "sample_image.jpg",
                "Sample Bio",
                "Sample TikTok",
                "Sample Instagram",
                "Sample Facebook",
                "2023-09-18",
                "2023-09-18"
            )
        }
    }

    override fun onTambahKeranjangClick(packet: Packet) {
        Log.d("TambahKeranjangClicked", "Packet: $packet")
        val socmed = intent.getStringExtra("tiktok")
        val influencerId = intent.getIntExtra("influencer_id", 0)
        val id_user = PreferenceUtility.getInt(application, "user_id", 0)

        Log.d("socmed", socmed.toString())

        val dataPacket = Order(
            id_influencer = influencerId,
            id_user = id_user,
        )

        apiService.orderKeranjang(dataPacket)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    Log.d("OrderKeranjangSuccess", "Response: $response")
                },
                { error ->
                    Log.e("OrderKeranjangError", "Error: ${error.message}")
                }
            )
    }
}
