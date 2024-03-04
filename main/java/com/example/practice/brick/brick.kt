package com.example.practice.brick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practice.databinding.ActivityBrickBinding
import java.util.*

class brick : AppCompatActivity() {
    private lateinit var binding: ActivityBrickBinding
    lateinit var inputname: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrickBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnStart.setOnClickListener(){
            Timer("Change",false).schedule(object : TimerTask(){
                override fun run() {
                }
            },250)

            var intent = Intent(this,arkanoid_main::class.java)
            startActivity(intent)
        }

    }
}