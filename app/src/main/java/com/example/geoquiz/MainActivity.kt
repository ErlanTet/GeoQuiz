package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTrue = findViewById(R.id.btn_true)
        btnFalse = findViewById(R.id.btn_false)

        btnTrue.setOnClickListener {
            Toast.makeText(this, R.string.correct, Toast.LENGTH_LONG).apply {
                setGravity(Gravity.TOP, 0, 0)
                show()
            }
        }

        btnFalse.setOnClickListener {
            Toast.makeText(this, R.string.incorrect, Toast.LENGTH_LONG).apply {
                setGravity(Gravity.TOP, 0, 0)
                show()
            }
        }
    }
}