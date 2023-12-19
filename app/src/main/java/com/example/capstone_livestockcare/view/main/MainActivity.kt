package com.example.capstone_livestockcare.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.capstone_livestockcare.R
import com.example.capstone_livestockcare.databinding.ActivityMainBinding
import com.example.capstone_livestockcare.view.ViewModelFactory
import com.example.capstone_livestockcare.view.WelcomeActivity
import com.example.capstone_livestockcare.view.fragment.HistoryFragment
import com.example.capstone_livestockcare.view.fragment.home.HomeFragment
import com.example.capstone_livestockcare.view.fragment.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSession()
    }

    private  fun getSession(){
        viewModel.getSession().observe(this){user->
            if(!user.isLogin){
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }else{
                bottomNavigationView = binding.navView

                bottomNavigationView.setOnItemSelectedListener { item ->
                    when(item.itemId){
                        R.id.homeNav -> {
                            replaceFragment(HomeFragment())
                            true
                        }
                        R.id.historyNav -> {
                            replaceFragment(HistoryFragment())
                            true
                        }
                        R.id.profileNav -> {
                            replaceFragment(ProfileFragment())
                            true
                        }
                        else -> false
                    }
                }
                replaceFragment(HomeFragment())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }
}