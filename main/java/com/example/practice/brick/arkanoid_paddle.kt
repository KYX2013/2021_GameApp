package com.example.practice.brick

import android.graphics.RectF
import com.example.practice.brick.arkanoid_game.Companion.xAcc

class arkanoid_paddle(private val screenX : Int, private val screenY : Int){
    private var rect : RectF
    private val length : Float
    private val height : Float
    private var x : Float
    private var y : Float
    private val paddleSpeed : Float
    private var paddleMoving : Int = STOPPED


    init {
        length = screenX/6f
        height = 30f
        x = screenX/2f
        y = screenY - 30f
        rect = RectF(x,y,x+length,y+height)
        paddleSpeed = 600f
    }

    companion object{
        const val STOPPED = 0
        const val LEFT = 1
        const val RIGHT = 2
    }

    fun getRect() : RectF{
        return rect
    }

    fun setMovementState(state : Int){
        paddleMoving = state
    }

    fun update(fps : Long){
        if (paddleMoving == LEFT){
            x -= paddleSpeed / fps
            if(x <= 0f){
                x = 0f
            }
        }
        if(paddleMoving == RIGHT){
            x += paddleSpeed / fps
            if(x >= screenX-length){
                x = screenX.toFloat()-length
            }
        }
        if(xAcc != 0f){
            x -= xAcc*200/ fps
            if(x >= screenX-length){
                x = screenX.toFloat()-length
            }
            if(x <= 0f){
                x = 0f
            }
        }
        rect.left = x
        rect.right = x + length
    }
}