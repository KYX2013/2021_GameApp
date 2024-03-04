package com.example.arknoid

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GamePlay: AppCompatActivity() {
    lateinit var sensorManager: SensorManager
    var sensor: Sensor?=null
    private val eventListener = object: SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?,p1: Int){}
        override fun onSensorChanged(event: SensorEvent?) {
            if (event==null ){
                Toast.makeText(this@GamePlay,"event is null !!",Toast.LENGTH_LONG).show()
            }
            when(event!!.sensor.type){
                Sensor.TYPE_ACCELEROMETER ->{
                    xAcc = event.values[0]
                }else ->{
                    Toast.makeText(this@GamePlay,"unknown sensor activated",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private lateinit var gameView: GameView

    companion object{
        var load: Boolean = false
        lateinit var sharedPref: SharedPreferences
        var xAcc: Float=0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = getSharedPreferences("gameState",Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        gameView = findViewById(R.id.Game)
    }

    override fun onPause() {
        super.onPause()
        unregisterSensor()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        registerSensor()
        gameView.resume()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        gameView.surfaceChanged(gameView.holder,PixelFormat.RGB_565,0,0)
        gameView.invalidate()
    }

    private fun registerSensor(){
        sensorManager.registerListener(eventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }
    private fun unregisterSensor(){
        sensorManager.unregisterListener(eventListener)
    }
}