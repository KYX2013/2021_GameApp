package com.example.practice.snake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.practice.R
import kotlinx.android.synthetic.main.activity_snakes.*

class snakes : AppCompatActivity(){
    lateinit var inputname: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snakes)

        btnStart.setOnClickListener(){
            inputname = edtScore.text.toString()
            textView.text = "GO!! " + inputname

            Handler().postDelayed({
                val intent = Intent(this, snakegame::class.java)
                intent.putExtra("Player",inputname)
                startActivity(intent)
            },250)

        }
    }
}