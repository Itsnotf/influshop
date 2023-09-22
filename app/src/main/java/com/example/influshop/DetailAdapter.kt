package com.example.influshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.influshop.databinding.IsiDetailOrderBinding
import com.example.influshop.model.Detail

class DetailAdapter(val data:ArrayList<Detail>) :RecyclerView.Adapter<DetailAdapter.ViewHolder>(){
    class ViewHolder (val viewBinder:IsiDetailOrderBinding):RecyclerView.ViewHolder(viewBinder.root){
        fun bind(item:Detail){
            viewBinder.etPostFeed.text = item.packet
            viewBinder.etDescription.text = item.description
            viewBinder.etPrice.text = item.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        IsiDetailOrderBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.isi_detail_order,parent,false)
        )
    )

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateData(newListData:List<Detail>){
        data.clear()
        data.addAll(newListData)
        notifyDataSetChanged()
    }
}