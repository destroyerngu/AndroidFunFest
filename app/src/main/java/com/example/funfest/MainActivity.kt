package com.example.funfest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.funfest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val model: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mViewModel = model
        binding.lifecycleOwner = this

        model.bind(binding)
    }
}