package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.ActivityDetailFoodBinding

class DetailFoodActivity : AppCompatActivity() {

    private val binding: ActivityDetailFoodBinding by lazy {
        ActivityDetailFoodBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}