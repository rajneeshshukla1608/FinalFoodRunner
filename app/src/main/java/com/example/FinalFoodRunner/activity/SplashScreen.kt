package com.example.FinalFoodRunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.FinalFoodRunner.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //        1st method to made delay mostly used to make the splash screen
//
//        Handler().postDelayed({
//            val startAct = Intent(this@splash, LoginActivity::class.java)
//            startActivity(startAct)
//        }, 2000)

        //this below method is more effective than above i love this method as said kotlin gives the easiness to do big work in less code

        object : CountDownTimer(2000, 1000){
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(intent)
            }
        }.start()
    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}