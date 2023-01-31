package com.greymatter.telugucalender.Activites


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.greymatter.telugucalender.Fragments.MoreOptionsFrag
import com.greymatter.telugucalender.Fragments.MuhurthaluFrag
import com.greymatter.telugucalender.Fragments.PandugaluFrag
import com.greymatter.telugucalender.Fragments.RashiPahlaluFrag
import com.greymatter.telugucalender.R
import com.greymatter.telugucalender.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    var activityHomeBinding: ActivityHomeBinding? = null
    private lateinit var mInterstitialAd: InterstitialAd
    private var interstitial: InterstitialAd? = null
    var handler: Handler? = null
    val adIRequest = AdRequest.Builder().build()

    companion object {
        var fm: FragmentManager? = null
        var navbar: BottomNavigationView? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding!!.root)
        fm = supportFragmentManager
        navbar = activityHomeBinding!!.BottomNavigation
        fm!!.beginTransaction().replace(
            com.greymatter.telugucalender.R.id.Container,
            com.greymatter.telugucalender.Fragments.panchangamFrag()
        ).commit()


        // Create an InterstitialAd object.
        mInterstitialAd = InterstitialAd(this)
        val adUnitId = resources.getString(R.string.appopen)
        mInterstitialAd.adUnitId = adUnitId


        // Load the interstitial ad.
        val adRequest = AdRequest.Builder().build()
        mInterstitialAd.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            // Load the ad again if it's not loaded yet.
            val adRequest = AdRequest.Builder().build()
            mInterstitialAd.loadAd(adRequest)
        }



        handler = Handler()
      playad()

        activityHomeBinding!!.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                com.greymatter.telugucalender.R.id.Panchangam -> {
                    fm!!.beginTransaction().replace(
                        R.id.Container,
                        com.greymatter.telugucalender.Fragments.panchangamFrag()
                    ).commit()
                    true
                }
                R.id.Pandugalu -> {
                    fm!!.beginTransaction().replace(R.id.Container, PandugaluFrag()).commit()
                    true
                }
                R.id.Muhurthalu -> {
                    fm!!.beginTransaction().replace(R.id.Container, MuhurthaluFrag()).commit()
                    true
                }
                R.id.RashiPhalalu -> {
                    fm!!.beginTransaction().replace(R.id.Container, RashiPahlaluFrag()).commit()
                    true
                }
                R.id.More -> {
                    fm!!.beginTransaction().replace(R.id.Container, MoreOptionsFrag()).commit()
                    true
                }
                else -> {
                    TODO()
                }
            }
        }
    }

    private fun playad() {
        MobileAds.initialize(this, getString(com.greymatter.telugucalender.R.string.admob_app_id));
        handler!!.postDelayed({
            interstitial = InterstitialAd(this@HomeActivity)
            interstitial!!.setAdUnitId(getString(R.string.admob_interstitial_id))
            interstitial!!.setAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    // Call displayInterstitial() function when the Ad loads
                    displayInterstitial()
                }
            })
          interstitial!!.loadAd(adIRequest)


            handler!!.postDelayed({
                playad()
            },10000)



        }, 30000)
    }




    private fun displayInterstitial() {
        // If Interstitial Ads are loaded then show them, otherwise do nothing.
        if (interstitial!!.isLoaded()) {
            interstitial!!.show();
        }
    }
}