package com.example.influshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.influshop.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // Observe LiveData and update UI when data is available
        viewModel.profileData.observe(viewLifecycleOwner, Observer { data ->
            // Update UI elements with data
            binding.etUsername.text = data.name
            binding.etJabatan.text = "User"
            binding.etNegara.text = "Indonesia"
            // Assuming binding.fotoProfil is the ImageView for the profile picture
            Glide.with(requireContext())
                .load("https://itfest.cyborgitcenter.org/profile_pictures/${data.profile_pict}") // Use the correct property from your data
                .into(binding.fotoProfil)
            binding.fotoProfil.visibility = View.VISIBLE
        })

        viewModel.fetchProfileData()
        logout()

        return view
    }

    private fun logout(){
        binding.btLogout.setOnClickListener{
            PreferenceUtility.clear(requireActivity().application)
            startActivity(Intent(requireContext(), loginActivity::class.java))
            requireActivity().finish() // Optional: Finish the current activity after logout
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
