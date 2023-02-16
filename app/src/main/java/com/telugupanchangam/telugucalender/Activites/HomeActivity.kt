package com.telugupanchangam.telugucalender.Activites


import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.telugupanchangam.telugucalender.Fragments.MoreOptionsFrag
import com.telugupanchangam.telugucalender.Fragments.MuhurthaluFrag
import com.telugupanchangam.telugucalender.Fragments.PandugaluFrag
import com.telugupanchangam.telugucalender.Fragments.RashiPahlaluFrag
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.ActivityHomeBinding
import com.telugupanchangam.telugucalender.helper.Constant
import com.telugupanchangam.telugucalender.helper.Session


class HomeActivity : AppCompatActivity() {
    var activityHomeBinding: ActivityHomeBinding? = null
    private var interstitial: InterstitialAd? = null
    var handler: Handler? = null
    val adIRequest = AdRequest.Builder().build()
    var session: Session? = null
    var i: Int=1;

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
        session = Session(applicationContext)
        fm!!.beginTransaction().replace(
            com.telugupanchangam.telugucalender.R.id.Container,
            com.telugupanchangam.telugucalender.Fragments.panchangamFrag()
        ).commit()



    }

    override fun onStart() {
        super.onStart()


        handler = Handler()
        MobileAds.initialize(this, getString(com.telugupanchangam.telugucalender.R.string.admob_app_id));

        interstitial = InterstitialAd(this@HomeActivity)
        interstitial!!.setAdUnitId(getString(R.string.admob_interstitial_id))




        playad()



        activityHomeBinding!!.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                com.telugupanchangam.telugucalender.R.id.Panchangam -> {
                    fm!!.beginTransaction().replace(
                        R.id.Container,
                        com.telugupanchangam.telugucalender.Fragments.panchangamFrag()
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



    private var lastAdDisplayedTime = 0L

    private fun playad() {


        val currentTime = System.currentTimeMillis()

        // Calculate the delay for the next ad display
        val timeSinceLastAd = currentTime - lastAdDisplayedTime
        val nextAdDelay = kotlin.math.max(0L, 30000L - timeSinceLastAd)

        // Schedule the next ad display
        handler!!.postDelayed({
            interstitial!!.loadAd(adIRequest)
        }, nextAdDelay)

        interstitial!!.setAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                // Retry loading the ad after 30 seconds
                handler!!.postDelayed({
                    playad()
                }, 30000)
            }

            override fun onAdClosed() {
                // Update the last ad displayed time
                lastAdDisplayedTime = System.currentTimeMillis()

                // Schedule the next ad display after 30 seconds
                handler!!.postDelayed({
                    playad()
                }, 30000)
            }

            override fun onAdOpened() {

            }

            override fun onAdLoaded() {
                // Update the last ad displayed time
                lastAdDisplayedTime = System.currentTimeMillis()

                // Display the ad after it is loaded
                displayInterstitial()
            }
        })

    }






    private fun displayInterstitial() {
        // If Interstitial Ads are loaded then show them, otherwise do nothing.
        if (interstitial!!.isLoaded()) {
            interstitial!!.show();
        }
    }



}