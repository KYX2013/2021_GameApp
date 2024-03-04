package com.example.arknoid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStart).setOnClickListener{
            startActivity(Intent(this,GamePlay::class.java))
        }
    }

    fun startGame(){
        startActivity(Intent(this,GamePlay::class.java))
    }
}