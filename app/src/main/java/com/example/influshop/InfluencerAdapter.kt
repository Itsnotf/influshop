package com.example.influshop

import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.influshop.databinding.IsiInfluencerBinding
import com.example.influshop.model.Influencer
class InfluencerAdapter(private val data: ArrayList<Influencer>) : RecyclerView.Adapter<InfluencerAdapter.ViewHolder>() {
    class ViewHolder(private val viewBinder: IsiInfluencerBinding) : RecyclerView.ViewHolder(viewBinder.root) {
        fun bind(item: Influencer) {
            viewBinder.namaInflu.text = item.name
            // Set a click listener for the TextView
            viewBinder.namaInflu.setOnClickListener {
                viewBinder.root.context.startActivity(Intent(viewBinder.root.context,KatalogActivity::class.java).putExtra("influencer_id", item.id).putExtra("tiktok",item.tiktok).putExtra("nama_influ",item.name).putExtra("bio",item.bio).putExtra("image",item.photo))
            }

            Glide.with(viewBinder.root.context)
                .load("https://itfest.cyborgitcenter.org/influencer_picture/${item.photo}")
                .error(R.drawable.ic_launcher_background)
                .into(viewBinder.fotoInflu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IsiInfluencerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateData(newListData: List<Influencer>) {
        data.clear()
        data.addAll(newListData)
        notifyDataSetChanged()
    }
}
