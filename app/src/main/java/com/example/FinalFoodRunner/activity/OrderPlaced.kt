package com.example.FinalFoodRunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.FinalFoodRunner.R

class OrderPlaced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)

        val placed :Button = findViewById(R.id.Done)
    placed.setOnClickListener {
        val intent = Intent(this@OrderPlaced,MainActivity::class.java)
        startActivity(intent)
    }
 }
    override fun onPause() {
        super.onPause()
    }
}