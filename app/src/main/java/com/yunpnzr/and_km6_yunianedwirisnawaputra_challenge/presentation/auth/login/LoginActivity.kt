package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityLoginBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.register.RegisterActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.main.MainActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutInputLogin.btnLogin.setOnClickListener {
            inputLogin()
        }
        binding.layoutInputLogin.tvNotHaveAccountRegister.setOnClickListener {
            navigateRegister()
        }
    }

    private fun inputLogin() {
        val email = binding.layoutInputLogin.etEmail.text.toString().trim()
        val password = binding.layoutInputLogin.etPassword.text.toString().trim()
        doLogin(email, password)
    }

    private fun doLogin(
        email: String,
        password: String,
    ) {
        loginViewModel.doLogin(email, password).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutInputLogin.pbLogin.isVisible = false
                    binding.layoutInputLogin.btnLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT,
                    ).show()
                    navigateToMain()
                },
                doOnLoading = {
                    binding.layoutInputLogin.pbLogin.isVisible = true
                    binding.layoutInputLogin.btnLogin.isEnabled = false
                },
                doOnError = {
                    binding.layoutInputLogin.pbLogin.isVisible = false
                    binding.layoutInputLogin.btnLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.login_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun navigateRegister() {
        startActivity(
            Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
