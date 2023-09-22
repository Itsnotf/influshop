package com.example.influshop

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.influshop.databinding.IsiHistoryBinding
import com.example.influshop.model.History

class HistoryAdapter(val data: ArrayList<History>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    class ViewHolder (val viewBinder: IsiHistoryBinding): RecyclerView.ViewHolder(viewBinder.root){
        fun bind(item: History){
            viewBinder.namaInflu.text = item.influencer
            viewBinder.tglOrder.text = item.created_at
            viewBinder.totalOrder.text = item.total
            viewBinder.tvLunas.text = item.payment_status

            viewBinder.btnButtonDetail.setOnClickListener{
                val intent = Intent(viewBinder.root.context, DetailOrderActivity::class.java)
                // You can add any necessary extras to the intent here
                viewBinder.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        IsiHistoryBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.isi_history,parent,false)
        )
    )

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
    fun updateData(newListData:List<History>){
        data.clear()
        data.addAll(newListData)
        notifyDataSetChanged()
    }
}