package com.example.capstone_livestockcare.view.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.capstone_livestockcare.R
import com.example.capstone_livestockcare.data.model.UserRegister
import com.example.capstone_livestockcare.data.response.RegisterResponse
import com.example.capstone_livestockcare.databinding.ActivityRegisterBinding
import com.example.capstone_livestockcare.view.ViewModelFactory
import com.example.capstone_livestockcare.view.WelcomeActivity
import com.example.capstone_livestockcare.view.login.LoginActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        setupView()
        setupAction()
        showLoading(false)
        emailFocusListener()
        passwordFocusListener()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            showLoading(true)
            val username = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmEditText.text.toString()

            if (username.isEmpty()) {
                showLoading(false)
                binding.nameContainer.error = getString(R.string.fill_username)
            } else if (email.isEmpty()) {
                showLoading(false)
                binding.emailContainer.error = getString(R.string.fill_email)
            } else if (password.isEmpty()) {
                showLoading(false)
                binding.passwordContainer.error = getString(R.string.fill_password)
            } else if (confirmPassword != password) {
                showLoading(false)
                binding.confirmContainer.error = getString(R.string.fill_confirmPassword)
            }

            lifecycleScope.launch {
                try {
                    val response = viewModel.register(username, email, password, confirmPassword)
                    showLoading(false)
                    showToast(response.message)
                    AlertDialog.Builder(this@RegisterActivity).apply {
                        setTitle("Yeah!")
                        setMessage(getString(R.string.succes_login))
                        setPositiveButton(getString(R.string.next)) { _, _ ->
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                } catch (e: HttpException) {
                    showLoading(false)
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                    showToast(errorResponse.message)
                }
            }


        }

        binding.signInText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbarRegister.visibility =
            if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        binding.btnRegister.isEnabled = !isLoading
    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.emailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = validPassword()
            }
        }
        binding.confirmEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.confirmContainer.helperText = validConfirmPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.passwordEditText.text.toString()
        if (passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
        return null
    }

    private fun validConfirmPassword(): String? {
        val passwordText = binding.passwordEditText.text.toString()
        val confirmPasswordText = binding.confirmEditText.text.toString()
        if (passwordText != confirmPasswordText) {
            return "Password does not match"
        }
        return null
    }

}