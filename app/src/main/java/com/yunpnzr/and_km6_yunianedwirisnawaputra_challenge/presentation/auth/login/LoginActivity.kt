package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}