package com.example.arknoid

import android.graphics.RectF
import com.example.arknoid.GamePlay.Companion.xAcc

class Paddle(
        private val screenX: Int,
        private val screenY: Int,
) {
    private var rect: RectF
    private val length: Float
    private val height: Float
    private var x: Float
    private var y: Float
    private var paddleSpeed: Float
    private var paddleMoving: Int = STOPPED

    init {
        length = screenX/6f
        height = 30f
        x = screenX/2f
        y = screenY - 30f
        rect = RectF(x,y,x+length,y+height)
        paddleSpeed = xAcc
    }

    companion object{
        const val STOPPED = 0
        const val LEFT = -1
        const val RIGHT = 1
    }

    fun getRect(): RectF{
        return rect
    }

    fun setMovingState(state: Int){
        paddleMoving = state
    }

    fun update(fps: Long){
        paddleSpeed = xAcc
        x = x + paddleSpeed/fps
        if (x>=screenX-length){
            x=screenX.toFloat()-length
        }else if(x<=0f){
            x=0f
        }
        rect.left = x
        rect.right = x+length
    }
}