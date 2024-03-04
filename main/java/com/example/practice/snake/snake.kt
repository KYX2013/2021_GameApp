package com.example.practice.snake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.practice.databinding.ActivitySnakeBinding
import java.util.*

class snake : AppCompatActivity(){
    private lateinit var binding: ActivitySnakeBinding
    lateinit var inputname: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnakeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnStart.setOnClickListener(){
            inputname = binding.edtScore.text.toString()
            binding.textView.text = "GO!! " + inputname

            Timer("Change",false).schedule(object :TimerTask(){
                override fun run() {
                }
            },250)

            val intent = Intent(this, snakegame::class.java)
            intent.putExtra("Player",inputname)
            startActivity(intent)
            binding.textView.text = "Greedy Snake"
        }
    }
}