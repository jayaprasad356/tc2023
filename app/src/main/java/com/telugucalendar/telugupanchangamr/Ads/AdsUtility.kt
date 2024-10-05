package com.telugucalendar.telugupanchangamr.Ads


import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdsUtility {
    private var mInterstitialAd: InterstitialAd? = null
    private var isInterstitialAdLoaded: Boolean = false

    fun loadInterstitialAd(context: Context?) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context!!,
            "ca-app-pub-1838677544163695/9386618975",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    isInterstitialAdLoaded = true
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    mInterstitialAd = null
                    isInterstitialAdLoaded = false
                }
            })
    }

    fun isInterstitialAdLoaded(): Boolean {
        return isInterstitialAdLoaded
    }

    fun showInterstitialAd(context: Context?, adClosedCallback: Runnable?) {
        if (isInterstitialAdLoaded) {
            mInterstitialAd?.show((context as Activity?)!!)
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    adClosedCallback?.run()
                }
            }
        } else {
            loadInterstitialAd(context)
            adClosedCallback?.run()
        }
    }
}

