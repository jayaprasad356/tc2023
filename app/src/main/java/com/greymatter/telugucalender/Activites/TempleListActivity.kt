package com.greymatter.telugucalender.Activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import com.greymatter.telugucalender.R

class TempleListActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temple_list)



        var cardtemple = findViewById<CardView>(R.id.cardtemple)
        cardtemple.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TempleinfoActivity::class.java)
            startActivity(intent)
        })
    }
}