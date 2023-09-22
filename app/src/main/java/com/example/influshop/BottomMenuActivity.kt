package com.example.influshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.influshop.databinding.ActivityBottomMenuBinding
import com.example.influshop.databinding.ActivityMainBinding

class BottomMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBottomMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(InfluencerFragment())

        binding.bottomMenu.setOnItemSelectedListener {

            when(it.itemId){

                R.id.menu_influ -> replaceFragment(InfluencerFragment())
                R.id.menu_cart -> replaceFragment(CartFragment())
                R.id.menu_history -> replaceFragment(HistoryFragment())
                R.id.menu_profile -> replaceFragment(ProfileFragment())

                else -> {


                }
            }

            true
        }


    }

    private fun replaceFragment(fragment : Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.dashboard,fragment)
        fragmentTransaction.commit()
    }

}