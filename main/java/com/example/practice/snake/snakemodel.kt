package com.example.practice.snake

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practice.snake.FindFlag.flag
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random.Default.nextInt

class snakemodel: ViewModel(){
    private val body = mutableListOf<Position>()
    private val size = 20
    private var score = 0
    private var delayTime = 550L
    private val periodChange = 15
    private var bonus: Position? =null
    var direction = Direction.LEFT
    var snake = MutableLiveData<List<Position>>()
    var bonusPosition = MutableLiveData<Position>()
    var gameState = MutableLiveData<GameState>()
    var scoreData = MutableLiveData<Int>()

    fun move(dir: Direction){
        direction = dir
    }

    fun start(){
        score = 0
        delayTime = 550L
        direction = Direction.LEFT

        body.clear()
        body.add(Position(10,10))
        body.add(Position(11,10))
        body.add(Position(12,10))
        body.add(Position(13,10))
        bonus = nextbonus().also {
            bonusPosition.value = it
        }
        startTimer()
    }
    private fun startTimer() {
        fixedRateTimer("timer",true,250, delayTime){
            val pos = body.first().copy().apply {
                when(direction){
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                    Direction.UP -> y--
                    Direction.DOWN -> y++
                }
                if( body.contains(this) || x<0 || x>=size || y<0 || y>=size ){
                    cancel()
                    gameState.postValue(GameState.gameover)
                }
            }
            body.add(0,pos)
            flag = true
            if(pos != bonus){
                body.removeAt(body.size-1)
            }else{
                bonus = nextbonus().also {
                    bonusPosition.postValue(it)
                }
                score++
                scoreData.postValue(score)
                delayTime-=periodChange
                if (delayTime < 20){
                    delayTime = 20
                }
                cancel()
                startTimer()
            }
            snake.postValue(body)
        }
    }
    fun nextbonus(): Position {
        var pos = Position(nextInt(size), nextInt(size))
        while( body.contains(pos) ){
            pos = Position(nextInt(size), nextInt(size))
        }
        return pos
    }

}

data class Position(var x: Int,  var y: Int)

enum class Direction{
    UP, DOWN, LEFT, RIGHT
}

enum class GameState{
    ongoing, gameover
}