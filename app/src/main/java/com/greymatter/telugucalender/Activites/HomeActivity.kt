package com.greymatter.telugucalender.Activites


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
import com.greymatter.telugucalender.Fragments.MoreOptionsFrag
import com.greymatter.telugucalender.Fragments.MuhurthaluFrag
import com.greymatter.telugucalender.Fragments.PandugaluFrag
import com.greymatter.telugucalender.Fragments.RashiPahlaluFrag
import com.greymatter.telugucalender.R
import com.greymatter.telugucalender.databinding.ActivityHomeBinding
import com.greymatter.telugucalender.helper.Constant
import com.greymatter.telugucalender.helper.Session


class HomeActivity : AppCompatActivity() {
    var activityHomeBinding: ActivityHomeBinding? = null
    private lateinit var mInterstitialAd: InterstitialAd
    private var interstitial: InterstitialAd? = null
    private var interstitial2: InterstitialAd? = null
    var handler: Handler? = null
    val adIRequest = AdRequest.Builder().build()
    val adIRequest2 = AdRequest.Builder().build()
    var session: Session? = null

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
            com.greymatter.telugucalender.R.id.Container,
            com.greymatter.telugucalender.Fragments.panchangamFrag()
        ).commit()



    }

    override fun onStart() {
        super.onStart()


        handler = Handler()
        MobileAds.initialize(this, getString(com.greymatter.telugucalender.R.string.admob_app_id));

        interstitial = InterstitialAd(this@HomeActivity)
        interstitial2 = InterstitialAd(this@HomeActivity)
        interstitial!!.setAdUnitId(getString(R.string.admob_interstitial_id))
        interstitial2!!.setAdUnitId(getString(R.string.appopen))




        playad()
        //displayInterstitial2()
//        if (session!!.getData(Constant.APP_OPEN_AD).equals("opened")){
//            playad()
//
//        }else{
//            displayInterstitial2()
//
//        }


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
        interstitial!!.loadAd(adIRequest)

        handler!!.postDelayed({
            displayInterstitial()
            interstitial!!.setAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {

                }
                override fun onAdClosed() {
                    playad()}
                override fun onAdOpened() {

                }
                override fun onAdLoaded() {

                }
            })



        }, 30000)
    }




    private fun displayInterstitial() {
        // If Interstitial Ads are loaded then show them, otherwise do nothing.
        if (interstitial!!.isLoaded()) {
            interstitial!!.show();
        }
    }

    override fun onStop() {
        super.onStop()
        session!!.setData(Constant.APP_OPEN_AD, "closed")
        interstitial2!!.loadAd(adIRequest2)
    }
    private fun displayInterstitial2() {
        Toast.makeText(applicationContext, "DISPLAYED", Toast.LENGTH_SHORT).show()


        // If Interstitial Ads are loaded then show them, otherwise do nothing.
        if (interstitial2!!.isLoaded()) {
            interstitial2!!.show();
            Toast.makeText(applicationContext, "SHOWN", Toast.LENGTH_SHORT).show()

            session!!.setData(Constant.APP_OPEN_AD, "opened")

        }
    }
}