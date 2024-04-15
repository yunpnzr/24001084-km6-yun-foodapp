package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy{
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}