package com.telugucalendar.telugupanchangamr.Activites

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import com.telugucalendar.telugupanchangamr.Fragments.BannerAdFragment
import com.telugucalendar.telugupanchangamr.Fragments.MainFragment
import com.telugucalendar.telugupanchangamr.Fragments.MoreOptionsFrag
import com.telugucalendar.telugupanchangamr.Fragments.MuhurthaluFrag
import com.telugucalendar.telugupanchangamr.Fragments.PandugaluFrag
import com.telugucalendar.telugupanchangamr.Fragments.RashiPahlaluFrag
import com.telugucalendar.telugupanchangamr.helper.Constant

import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {

    private var activityHomeBinding: ActivityHomeBinding? = null
    private var handler: Handler? = null
    private var session: com.telugucalendar.telugupanchangamr.helper.Session? = null
    private var i = 1
    private lateinit var adView: AdView

    var mNativeAd: NativeAd? = null


    companion object {
        private const val TAG = "--->Native Ad"
        @kotlin.jvm.JvmField
        var fm: FragmentManager? = null
        var navbar: BottomNavigationView? = null
    }

    val ONESIGNAL_APP_ID = "2f875f60-5def-4d14-bcba-d601a0e5eb99"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding?.root)

        loadNativeAd()

        fm = supportFragmentManager
        navbar = activityHomeBinding?.BottomNavigation
        session = com.telugucalendar.telugupanchangamr.helper.Session(applicationContext)
        fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()


        // Add BannerAdFragment dynamically
        val bannerAdFragment = BannerAdFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.bannerAdContainer, bannerAdFragment)
            .commit()

        activityHomeBinding?.BottomNavigation?.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Panchangam -> {
                    fm?.beginTransaction()?.replace(
                        R.id.Container,
                        com.telugucalendar.telugupanchangamr.Fragments.panchangamFrag()
                    )?.commit()
                    true
                }
                R.id.Pandugalu -> {
                    fm?.beginTransaction()?.replace(R.id.Container, PandugaluFrag())?.commit()
                    true
                }
                R.id.Muhurthalu -> {
                    fm?.beginTransaction()?.replace(R.id.Container, MuhurthaluFrag())?.commit()
                    true
                }
                R.id.RashiPhalalu -> {
                    fm?.beginTransaction()?.replace(R.id.Container, RashiPahlaluFrag())?.commit()
                    true
                }
                R.id.More -> {
                    fm?.beginTransaction()?.replace(R.id.Container, MoreOptionsFrag())?.commit()
                    true
                }
                else -> false
            }
        }


        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)

        }



    }






    override fun onBackPressed() {

        if (fm?.findFragmentById(R.id.Container) is MainFragment) {

            exitdialog()
            loadNativeAd()

            // Show the exit dialog
       }
        //        else if (fm?.findFragmentById(R.id.Container) is PandugaluFrag) {
//
//            fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()
//
//        } else if (fm?.findFragmentById(R.id.Container) is MuhurthaluFrag) {
//
//            fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()
//
//        } else if (fm?.findFragmentById(R.id.Container) is RashiPahlaluFrag) {
//
//            fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()

     //   }

    else {

            super.onBackPressed()
            loadNativeAd()


        }


    }

    private fun exitdialog() {

        val exitBuilderDialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.exit_layout, null)
        val yesBtm = dialogView.findViewById<Button>(R.id.id_exit_pos_btm)
        val noBtm = dialogView.findViewById<Button>(R.id.id_exit_neg_btm)
        val txtTitle = dialogView.findViewById<TextView>(R.id.id_exit_title)
        val txtMessage = dialogView.findViewById<TextView>(R.id.id_exit_message)
        val template = dialogView.findViewById<com.telugucalendar.telugupanchangamr.Ads.TemplateView>(R.id.my_template)
        val rateUsBtm = dialogView.findViewById<Button>(R.id.id_rate_us_btm)


        rateUsBtm.setOnClickListener {
            // Open Play Store for rating
            val appPackageName = packageName
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (e: ActivityNotFoundException) {
                // If Play Store is not available, open the app page on a browser
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }


        template.visibility = View.GONE
        if (mNativeAd != null) {
//            val styles = NativeTemplateStyle.Builder().build()
//            template.setStyles(styles)
            template.visibility = View.VISIBLE
            template.setNativeAd(mNativeAd)
        }
        txtMessage.text = "Do you want to exit?"
        exitBuilderDialog.setView(dialogView)
        val alertDialog = exitBuilderDialog.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
        yesBtm.setOnClickListener {
            val intentExit = Intent(Intent.ACTION_MAIN)
            intentExit.addCategory(Intent.CATEGORY_HOME)
            intentExit.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intentExit)
            finish()
            System.exit(0)
        }
        noBtm.setOnClickListener {
            if (mNativeAd != null) {
                mNativeAd!!.destroy()
            }
            loadNativeAd()
            alertDialog.cancel()
        }

    }

    private fun loadNativeAd() {
        val adLoader = AdLoader.Builder(this@HomeActivity, "ca-app-pub-1838677544163695/2058847194")
 //       val adLoader = AdLoader.Builder(this@HomeActivity, " ca-app-pub-1838677544163695/2058847194")
            .forNativeAd(NativeAd.OnNativeAdLoadedListener { nativeAd ->
                Log.d(TAG, "Native Ad Loaded")
                if (isDestroyed) {
                    nativeAd.destroy()
                    Log.d(TAG, "Native Ad Destroyed")
                    return@OnNativeAdLoadedListener
                }
                mNativeAd = nativeAd
            })
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                    Log.d(TAG, "Native Ad Failed To Load")
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            )
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }


}
