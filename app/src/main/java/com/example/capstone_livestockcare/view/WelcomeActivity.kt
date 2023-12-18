package com.example.capstone_livestockcare.view

import android.app.TaskStackBuilder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstone_livestockcare.databinding.ActivityWelcomeBinding
import com.example.capstone_livestockcare.view.login.LoginActivity
import com.example.capstone_livestockcare.view.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonJoin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
            //back stack
//            val stackBuilder = TaskStackBuilder.create(this)
//            stackBuilder.addNextIntentWithParentStack(intent)
//            stackBuilder.startActivities()

        }
        binding.signInText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            //back stack
//            val stackBuilder = TaskStackBuilder.create(this)
//            stackBuilder.addNextIntentWithParentStack(intent)
//            stackBuilder.startActivities()
        }
    }
}