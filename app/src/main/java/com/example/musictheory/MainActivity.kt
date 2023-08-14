package com.example.musictheory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val earTrainingBtn = findViewById<Button>(R.id.earTrainingBtn)
        val scaleBtn = findViewById<Button>(R.id.scaleBtn)
        earTrainingBtn.setOnClickListener{
            val Intent = Intent(this, earTrainingMainMenu::class.java)
            startActivity(Intent)

        }
        scaleBtn.setOnClickListener {
            val Intent = Intent (this, ScalesMenu::class.java)
            startActivity(Intent)
        }
    }
}