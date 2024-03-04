package com.example.practice.brick

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.practice.bottle.bottle
import com.example.practice.brick.arkanoid_game.Companion.LOAD
import com.example.practice.databinding.ActivityBrickBinding

class arkanoid_main : AppCompatActivity() {
    private lateinit var binding: ActivityBrickBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        binding = ActivityBrickBinding.inflate(layoutInflater)
        val viewB = binding.root
        setContentView(viewB)

        binding.button.setOnClickListener {
            startGame()
        }
        binding.button2.setOnClickListener {
            loadGame()
        }

    }

    fun startGame(){
        val intent1 = Intent(this, arkanoid_game::class.java)
        startActivity(intent1)
    }

    fun loadGame(){
        val intent2 = Intent(this, arkanoid_game::class.java)
        intent2.putExtra(LOAD, true)
        startActivity(intent2)
    }
}