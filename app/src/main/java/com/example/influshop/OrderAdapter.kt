package com.example.influshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.influshop.databinding.IsiKeranjangBinding
import com.example.influshop.model.Cart

class OrderAdapter(private val data:ArrayList<Cart>): RecyclerView.Adapter<OrderAdapter.ViewHolder>(){
    class ViewHolder(private val viewBinder: IsiKeranjangBinding): RecyclerView.ViewHolder(viewBinder.root){
        fun bind(item: Cart) {
            viewBinder.etDescription.text = item.todo
            viewBinder.etPostFeed.text = item.influencer_name
            viewBinder.etPrice.text = item.price.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IsiKeranjangBinding.inflate(
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

    fun updateData(newListData: List<Cart>){
        data.clear()
        data.addAll(newListData)
        notifyDataSetChanged()
    }
}