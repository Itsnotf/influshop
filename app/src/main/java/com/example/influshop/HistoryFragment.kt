package com.example.influshop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.influshop.api.ApiService
import com.example.influshop.databinding.FragmentHistoryBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    private lateinit var viewBinder:FragmentHistoryBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var historyAdapter:HistoryAdapter
    private val apiService = ApiService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinder = FragmentHistoryBinding.inflate(inflater,container,false)
        return viewBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_user = PreferenceUtility.getInt(requireActivity().application,"user_id",0)

        recyclerView = viewBinder.rvHistory
        historyAdapter = HistoryAdapter(ArrayList())

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = historyAdapter


        apiService.history(id_user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("AmbilHistorySuccess", "Response: $response")
                val history = response.data
                historyAdapter.updateData(history)
            },{
                error->
                Log.e("AmbilOrderError", "Error: ${error.message}")
            })
    }
}