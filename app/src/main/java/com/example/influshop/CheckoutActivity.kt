package com.example.influshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.influshop.databinding.ActivityCheckoutBinding
import com.example.influshop.model.Payment

class CheckoutActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        setupView()
    }
    private fun setupView(){
        val amount = PreferenceUtility.getInt(application,"amount",0);
        viewBinder.etTotalHarga.text = "Total : ${amount}"
        viewBinder.btProses.setOnClickListener{
            proses()
            startActivity(Intent(viewBinder.root.context,PembayaranActivity::class.java))
        }
    }

    private fun proses() {
        val nama_perusahaan = viewBinder.etUsername.text.toString()
        val sosmed_perusahaan = viewBinder.etSocmedPerusahaan.text.toString()
        val deskripsi_bisnis = viewBinder.etDeskripsiBisnis.text.toString()
        val pesan_influencer = viewBinder.etPesanUntukInfluencer.text.toString()
        PreferenceUtility.putString(application,"nama_perusahaan",nama_perusahaan)
        PreferenceUtility.putString(application,"sosmed_perusahaan",sosmed_perusahaan)
        PreferenceUtility.putString(application,"deskripsi_bisnis",deskripsi_bisnis)
        PreferenceUtility.putString(application,"pesan_influencer",pesan_influencer)
    }
}