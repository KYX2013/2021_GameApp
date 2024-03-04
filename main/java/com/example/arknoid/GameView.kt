package com.example.arknoid

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import com.example.arknoid.GamePlay.Companion.load
import com.example.arknoid.GamePlay.Companion.sharedPref

class GameView(
        context: Context,
        attrs: AttributeSet
):SurfaceView(context, attrs), SurfaceHolder.Callback {
    private lateinit var thread: GameThread
//    @Volatile
//    private var acce_x: Float = 0f
//    @Volatile
//    private var moving: Boolean = false

    private var screenX: Int = 0
    private var screenY: Int = 0
    private lateinit var paddle: Paddle
    private lateinit var ball: Ball
    private lateinit var bricks: ArrayList<Brick>
    private var numBricks: Int = 0
    private var levelScore: Int = 0
    private var totalScore: Int = 0
    private var lives: Int = 0
    private var win: Boolean = false
    private var lose: Boolean = false
    private var invisible: String = ""

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        if (load){
            lives = sharedPref.getInt("lives",0)
            totalScore = sharedPref.getInt("ts",0)
            levelScore = sharedPref.getInt("ls",0)
            invisible = sharedPref.getString("invisible","").toString()
        }
        this.isFocusable = true
        screenX = width
        screenY = height
        paddle = Paddle(screenX,screenY)
        ball = Ball(screenX,screenY)
        thread = GameThread(holder,this)
        thread.setRunning(true)
        thread.start()
        createBrickAndRestart(true)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        screenX = width
        screenY = height
        surfaceCreated(holder)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.setRunning(false)
        thread.join()
    }

    fun update(fps: Long){
        paddle.update(fps)
        ball.update(fps)

        for(i in 0 until numBricks){
            if(bricks[i].getVisibility()){
                if(RectF.intersects(bricks[i].getRect(),ball.getRect())){
                    bricks[i].setInvisible()
                    ball.reverseY()
                    levelScore += 10
                    totalScore += 10
                }
            }
        }

        if (RectF.intersects(paddle.getRect(),ball.getRect())){
            ball.setRandomXVel()
            ball.reverseY()
            ball.clearObstacleY(paddle.getRect().top - 2f)
        }

        if(ball.getRect().bottom > screenY){
            ball.reverseY()
            ball.clearObstacleY(screenY - 2f)
            lives --
            if(lives == 0){
                lose = true
                thread.setPaused(true)
                createBrickAndRestart(false)
            }
        }

        if(ball.getRect().top < 0){
            ball.reverseY()
            ball.clearObstacleY(22f)
        }

        if(ball.getRect().left < 0){
            ball.reverseX()
            ball.clearObstacleX(2f)
        }

        if(ball.getRect().right > screenX - 10){
            ball.reverseX()
            ball.clearObstacleX(screenX - 32f)
        }

        if(levelScore == numBricks*10){
            win = true
            thread.setPaused(true)
            createBrickAndRestart(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        val paintBall = Paint()
        val paintBrick = Paint()
        val paintPaddle = Paint()
        paintBall.color = resources.getColor(R.color.white, resources.newTheme())
        paintBrick.color = resources.getColor(R.color.Brick, resources.newTheme())
        paintPaddle.color = resources.getColor(R.color.yellow, resources.newTheme())

        try {
            canvas!!.drawColor(resources.getColor(R.color.black, resources.newTheme()))
            canvas.drawRect(paddle.getRect(), paintPaddle)
            canvas.drawRect(ball.getRect(), paintBall)

            for (i in 0..numBricks) {
                if (bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paintBrick)
                }
            }

            paintBall.textSize = 50f
            canvas.drawText("Score:" + totalScore + " Lives: " + lives , 10f, 50f, paintBall)
            if (win) {
                paintBall.textSize = 100f
                canvas.drawText("You've Won!!", 10f, screenY / 2f, paintBall)
            }
            if (lose) {
                paintBall.textSize = 100f
                canvas.drawText("You've Lost!!", 10f, screenY / 2f, paintBall)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun pause(){
        thread.setRunning(false)
        try {
            thread.join()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun resume(){
        thread = GameThread(holder,this)
        thread.setRunning(true)
        thread.start()
    }

    private fun createBrickAndRestart(isRestore: Boolean){
        ball.reset(screenX,screenY)
        val brickWidth = screenX/10
        val brickHeight = screenY/10

        bricks = ArrayList(200)
        numBricks = 0
        if(!isRestore){
            levelScore = 0
        }

        for (column in 0..9){
            for(row in 0..4){
                bricks.add(numBricks,Brick(row,column,brickWidth,brickHeight))
                numBricks ++
                if(isRestore && invisible.length >= numBricks){
                    if(invisible[numBricks-1] == '1'){
                        bricks[numBricks - 1].setInvisible()
                    }
                }
            }
        }
        if (lives == 0){
            totalScore = 0
            lives = 3
        }
    }
}