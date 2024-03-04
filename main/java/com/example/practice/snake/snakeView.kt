package com.example.practice.snake

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class snakeView(context: Context, attrs: AttributeSet): View(context,attrs) {
    private val paintBonus = Paint().apply { color = Color.rgb(186,31,93) }
    private val paint: Paint = Paint().apply { color = Color.BLACK }
    var snakeBody: List<Position>? = null
    var bonus: Position? = null
    var dim = 0
    var gap = 5

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            bonus?.apply {
                drawRect( (x*dim+gap).toFloat(), (y*dim+gap).toFloat(), ((x+1)*dim-gap).toFloat(), ((y+1)*dim-gap).toFloat(),paintBonus)
            }
            snakeBody?.forEach { pos ->
                drawRect((pos.x*dim+gap).toFloat(), (pos.y*dim+gap).toFloat(), ((pos.x+1)*dim-gap).toFloat(),((pos.y+1)*dim-gap).toFloat(),paint)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        dim = width/20
    }
    fun updateBonus( pos: Position? ){
        bonus = pos
        invalidate()
    }
}