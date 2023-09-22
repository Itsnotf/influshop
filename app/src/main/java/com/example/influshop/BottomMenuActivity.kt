package com.example.influshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.influshop.databinding.ActivityBottomMenuBinding

class BottomMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBottomMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(InfluencerFragment())

        binding.bottomMenu.setOnItemSelectedListener {

            when(it.itemId){

                R.id.menu_myshop -> replaceFragment(InfluencerFragment())
                R.id.menu_profiles -> replaceFragment(CartFragment())
                R.id.menu_history -> replaceFragment(HistoryFragment())
                R.id.menu_shopping_card -> replaceFragment(ProfileFragment())

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