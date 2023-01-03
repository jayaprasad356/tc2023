package com.greymatter.telugucalender.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.greymatter.telugucalender.Fragments.*
import com.greymatter.telugucalender.R
import com.greymatter.telugucalender.databinding.ActivityHomeBinding
import java.lang.NullPointerException

class HomeActivity : AppCompatActivity() {
    var activityHomeBinding : ActivityHomeBinding? = null
    companion object {
        var fm : FragmentManager? = null
        var navbar: BottomNavigationView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding!!.root)
        fm = supportFragmentManager
        navbar = activityHomeBinding!!.BottomNavigation
        fm!!.beginTransaction().replace(R.id.Container,com.greymatter.telugucalender.Fragments.panchangamFrag()).commit()

        activityHomeBinding!!.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Panchangam -> {
                    fm!!.beginTransaction().replace(R.id.Container,com.greymatter.telugucalender.Fragments.panchangamFrag()).commit()
                    true
                }
                R.id.Pandugalu -> {
                    fm!!.beginTransaction().replace(R.id.Container,com.greymatter.telugucalender.Fragments.PandugaluFrag()).commit()
                    true
                    }
                R.id.Muhurthalu -> {
                    fm!!.beginTransaction().replace(R.id.Container,MuhurthaluFrag()).commit()
                    true
                }
                R.id.RashiPhalalu -> {
                    fm!!.beginTransaction().replace(R.id.Container,RashiPahlaluFrag()).commit()
                    true
                }
                R.id.More -> {
                    fm!!.beginTransaction().replace(R.id.Container,MoreOptionsFrag()).commit()
                    true
                }
                else -> {
                    TODO()
                }
            }
        }
    }
}