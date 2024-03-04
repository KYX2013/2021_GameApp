package com.example.practice.bottle

import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.example.practice.databinding.ActivityBottleBinding
import java.util.*
import kotlin.math.pow


class bottle : AppCompatActivity() {
    var angle = 0
    var restart = false
    private lateinit var binding: ActivityBottleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bGo.setOnClickListener() {
            // 判斷是 GO 還是 RESET
            if (restart) {
                angle = angle % 360
                if(angle<0){
                    angle = angle + 360
                }
                // toDegree = 360 與 toDegree = 0 的差別，試試看就知道
                // 另外 pivotXValue 與 pivotXValue 若非 0.5f，也請試試看
                if(angle<180){
                    val rotate = RotateAnimation(
                            angle.toFloat(), 0f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f
                    )
                    rotate.fillAfter = true
                    rotate.duration = 1000
                    //動畫速度由慢到快，再到慢
                    rotate.interpolator = AccelerateDecelerateInterpolator()

                    // 動畫開始
                    binding.ivBottle.startAnimation(rotate)
                    binding.bGo.setText("GO")
                    restart = false
                }
                else {
                    val rotate = RotateAnimation(
                            angle.toFloat(), 360f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f
                    )
                    rotate.fillAfter = true
                    rotate.duration = 1000
                    //動畫速度由慢到快，再到慢
                    rotate.interpolator = AccelerateDecelerateInterpolator()

                    // 動畫開始
                    binding.ivBottle.startAnimation(rotate)
                    binding.bGo.setText("GO")
                    restart = false
                }
            } else {
                // 瓶子的轉動度數
                var degree = Random().nextInt(2)
                angle = ((Random().nextInt(3600))*((-1).toDouble().pow(degree))).toInt()
                val rotate = RotateAnimation(
                        0f, angle.toFloat(),
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f
                )
                rotate.fillAfter = true
                rotate.duration = 3600
                rotate.interpolator = AccelerateDecelerateInterpolator()

                // 動畫開始
                binding.ivBottle.startAnimation(rotate)
                restart = true
                binding.bGo.setText("RESET")
            }
        }
    }
}