package com.greymatter.telugucalender.Activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.greymatter.telugucalender.R

class TempleinfoActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_templeinfo)

        var imgbackbttn = findViewById<ImageView>(R.id.imgbackbttn);

        imgbackbttn.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }
}


