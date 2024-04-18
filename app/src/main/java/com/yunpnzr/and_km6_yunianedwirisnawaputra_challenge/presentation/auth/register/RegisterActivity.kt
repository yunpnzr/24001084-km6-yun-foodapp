package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.AuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.FirebaseAuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.firebase.FirebaseServices
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.firebase.FirebaseServicesImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityRegisterBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login.LoginActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.main.MainActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.GenericViewModelFactory
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy{
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels{
        val service: FirebaseServices = FirebaseServicesImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(RegisterViewModel(repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
        //setForm()
    }

    private fun setClickListener() {
        binding.layoutInputRegister.btnRegister.setOnClickListener {
            inputRegister()
        }
        binding.layoutInputRegister.tvHaveAccount.setOnClickListener{
            navigateLogin()
        }
    }

    private fun inputRegister() {
        val name = binding.layoutInputRegister.etNameRegister.text.toString().trim()
        val email = binding.layoutInputRegister.etEmailRegister.text.toString().trim()
        val password = binding.layoutInputRegister.etPasswordRegister.text.toString().trim()
        val numberPhone = binding.layoutInputRegister.etTelephone.text.toString().trim()
        doRegister(name, email, password, numberPhone)
    }

    private fun doRegister(name: String, email: String, password: String, numberPhone: String) {
        viewModel.doRegister(name, email, password, numberPhone).observe(this){ result->
            result.proceedWhen (
                doOnSuccess = {
                    binding.layoutInputRegister.pbRegister.isVisible = false
                    binding.layoutInputRegister.btnRegister.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.register_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateLogin()
                },
                doOnLoading = {
                    binding.layoutInputRegister.pbRegister.isVisible = true
                    binding.layoutInputRegister.btnRegister.isEnabled = false
                },
                doOnError = {
                    binding.layoutInputRegister.pbRegister.isVisible = false
                    binding.layoutInputRegister.btnRegister.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.register_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateLogin() {
        startActivity(Intent(this,LoginActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }
}