package com.example.atividade_gps

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Define o layout XML

        // Acessa o TextView do layout
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "Hello World!"
    }
}