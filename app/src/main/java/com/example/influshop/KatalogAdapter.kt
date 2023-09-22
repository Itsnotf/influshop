package com.example.influshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.influshop.databinding.IsiKatalogBinding
import com.example.influshop.model.Packet

class KatalogAdapter(
    private val data: List<Packet>,
    private val listener: OnTambahKeranjangClickListener
) : RecyclerView.Adapter<KatalogAdapter.ViewHolder>() {

    interface OnTambahKeranjangClickListener {
        fun onTambahKeranjangClick(packet: Packet)
    }

    inner class ViewHolder(private val viewBinder: IsiKatalogBinding) :
        RecyclerView.ViewHolder(viewBinder.root) {

        fun bind(item: Packet) {
            viewBinder.postFeed.text = item.packet_name
            viewBinder.harga.text = item.price
            viewBinder.deskripsi.text = item.description
            viewBinder.fotoPacket.setImageResource(R.drawable.ic_launcher_background)

            Glide.with(viewBinder.root.context)
                .load("https://itfest.cyborgitcenter.org/picture_packets/${item.picture}")
                .error(R.drawable.ic_launcher_background)
                .into(viewBinder.fotoPacket)
            viewBinder.tambahKeranjang.setOnClickListener {
                listener.onTambahKeranjangClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinder = IsiKatalogBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinder)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val packet = data[position]
        holder.bind(packet)
    }
}
