package com.example.arknoid

import android.graphics.RectF
import java.util.*

class Ball(
        private val sceenX: Int,
        private val sceenY: Int
) {
    private val rect: RectF
    private var xVelocity: Float
    private var yVelocity: Float
    private val ballWidth = 25f
    private val ballHeight = 25f

    init {
        rect = RectF()
        xVelocity = 200f
        yVelocity = -400f
    }

    fun getRect():RectF{
        return rect
    }

    fun update(fps: Long){
        rect.left = rect.left + (xVelocity/fps)
        rect.top = rect.top + (yVelocity/fps)
        rect.right = rect.left + ballWidth
        rect.bottom = rect.top + ballHeight
    }

    fun reverseY(){
        yVelocity *= -1
    }

    fun reverseX(){
        xVelocity *= -1
    }

    fun setRandomXVel(){
        val generator: Random = Random(System.nanoTime())
        val answer: Int = generator.nextInt(2)
        if (answer == 0){
            reverseX()
        }
    }

    fun clearObstacleY(y: Float){
        rect.bottom = y
        rect.top = y - ballHeight
    }

    fun clearObstacleX(x: Float){
        rect.left = x
        rect.right = x + ballWidth
    }

    fun reset(x: Int,y: Int){
        rect.left = x/2f
        rect.top = y - 20f
        rect.right = rect.left + ballWidth
        rect.bottom = rect.top + ballHeight
        xVelocity = 200f
        yVelocity = -400f
    }
}