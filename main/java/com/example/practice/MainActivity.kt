package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practice.bottle.bottle
import com.example.practice.brick.arkanoid_main
import com.example.practice.databinding.ActivityMainBinding
import com.example.practice.memory.Memory
import com.example.practice.snake.snake

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.SnakeButton.setOnClickListener {
            val intent = Intent(this, snake::class.java)
            startActivity(intent)
        }

        binding.brickButton.setOnClickListener {
            val intent = Intent(this, arkanoid_main::class.java)
            startActivity(intent)
        }

        binding.bottleButton.setOnClickListener {
            val intent = Intent(this, bottle::class.java)
            startActivity(intent)
        }

        binding.memoryButton.setOnClickListener {
            startActivity(Intent(this, Memory::class.java))
        }
    }
}