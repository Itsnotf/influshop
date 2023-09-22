package com.example.influshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.influshop.api.ApiService
import com.example.influshop.databinding.FragmentCartBinding
import com.example.influshop.model.Cart
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private val apiService = ApiService()
    private val formatter = DecimalFormat("Rp #,###")
    private var total: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return  binding.root
    }

    private fun bayar(){
        binding.btnPayment.setOnClickListener{
            PreferenceUtility.putInt(requireActivity().application,"amount",total)
            startActivity(Intent(binding.root.context, CheckoutActivity::class.java))
        }
    }

    private fun hitungTotal(dataCart:List<Cart>){
        dataCart.forEach{
            total += it.price
        }
        val infoTotal = "Total : ${formatter.format(total)}"
        binding.tvTotal.text =infoTotal
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id_user = PreferenceUtility.getInt(requireActivity().application, "user_id", 0)
        recyclerView = binding.rvCart
        orderAdapter = OrderAdapter(ArrayList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = orderAdapter

        bayar()
        apiService.ambilOrderByIdUser(id_user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("AmbilOrderSuccess", "Response: $response")
                val orders = response.data // Extract the list of orders from the response
                hitungTotal(orders)
                orderAdapter.updateData(orders)
            }, { error ->
                Log.e("AmbilOrderError", "Error: ${error.message}")
            })

    }

}
