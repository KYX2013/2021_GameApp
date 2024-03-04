package com.example.practice.brick;

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practice.R
import com.example.practice.databinding.ActivityArkanoidMainBinding

class arkanoid_game : AppCompatActivity() {
    lateinit var sensorManager: SensorManager
    var sensor: Sensor?=null
    private val eventListener = object: SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int){}
        override fun onSensorChanged(event: SensorEvent?) {
            if (event==null ){
                Toast.makeText(this@arkanoid_game,"event is null !!", Toast.LENGTH_LONG).show()
            }
            when(event!!.sensor.type){
                Sensor.TYPE_ACCELEROMETER ->{
                    xAcc = event.values[0]
                }else ->{
                Toast.makeText(this@arkanoid_game,"unknown sensor activated", Toast.LENGTH_SHORT).show()
            }
            }
        }
    }

    private lateinit var binding: ActivityArkanoidMainBinding
    private lateinit var gameView : arkanoid_view
    private val PREFS_NAME = "gameState"

    companion object{
        const val LOAD = "load"
        var load : Boolean = false
        lateinit var sharedPref : SharedPreferences
        var xAcc: Float = 0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        load = intent.getBooleanExtra(LOAD, false)
        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        binding = ActivityArkanoidMainBinding.inflate(layoutInflater)
        val viewb = binding.root
        setContentView(viewb)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        gameView = findViewById(R.id.ArkanoidGame)
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
        gameView.saveState()
        load = true
        gameView.surfaceChanged(gameView.holder, PixelFormat.RGB_565, 0,0)
        gameView.invalidate()
    }

    private fun registerSensor(){
        sensorManager.registerListener(eventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }
    private fun unregisterSensor(){
        sensorManager.unregisterListener(eventListener)
    }

}
