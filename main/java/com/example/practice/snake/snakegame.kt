package com.example.practice.snake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.practice.R
import com.example.practice.databinding.ActivitySnakegameBinding
import com.example.practice.snake.FindFlag.flag
import kotlinx.android.synthetic.main.activity_snakegame.*

class snakegame : AppCompatActivity() {

    private lateinit var binding: ActivitySnakegameBinding
    private lateinit var viewModel: snakemodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnakegameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val Playername = intent.getStringExtra("Player")
        if(Playername!="") {
            binding.player.text = Playername
        }

        var SCORE = 0
        flag = true

        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(snakemodel::class.java)
        viewModel.snake.observe(this, Observer{
            binding.game.snakeBody = it
            binding.game.invalidate()
        })
        viewModel.bonusPosition.observe(this, Observer {
            binding.game.updateBonus(it)
        })
        viewModel.scoreData.observe(this, Observer {
            binding.scoreview.setText(it.toString())
            SCORE=it.toInt()
        })
        viewModel.gameState.observe(this, Observer {
            if(it == GameState.gameover){
                AlertDialog.Builder(this)
                        .setTitle("Game Over.")
                        .setMessage("Player:"+ Playername +"\nScore:" + SCORE)
                        .setPositiveButton("Replay"){ dialog, which ->
                            viewModel.start()
                            binding.scoreview.text = "SCORE"
                        }.setNeutralButton("Cancel"){ dialog, which ->
                            finish()
                        }.show()
            }
        })
        viewModel.start()
        binding.left.setOnClickListener {
            if(viewModel.direction == Direction.RIGHT || !flag){}
            else{
                viewModel.move(Direction.LEFT)
                flag = false
            }
        }
        binding.right.setOnClickListener {
            if(viewModel.direction == Direction.LEFT || !flag){}
            else{
                viewModel.move(Direction.RIGHT)
                flag = false
            }
        }
        binding.up.setOnClickListener {
            if(viewModel.direction == Direction.DOWN || !flag){}
            else{
                viewModel.move(Direction.UP)
                flag = false
            }
        }
        binding.down.setOnClickListener {
            if(viewModel.direction == Direction.UP || !flag){}
            else{
                viewModel.move(Direction.DOWN)
                flag = false
            }
        }
    }
}
