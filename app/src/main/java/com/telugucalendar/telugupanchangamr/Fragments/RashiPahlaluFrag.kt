package com.telugucalendar.telugupanchangamr.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentRashiPahlaluBinding


import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class RashiPahlaluFrag : Fragment() {

    var month_year = ""
    var month = ""
    var year = ""
    var montharray = arrayOf(
        "జనవరి ",
        "ఫిబ్రవరి ",
        "మార్చి ",
        "ఏప్రిల్ ",
        "మే ",
        "జూన్ ",
        "జూలై ",
        "ఆగస్టు ",
        "సెప్టెంబర్ ",
        "అక్టోబర్ ",
        "నవంబర్ ",
        "డిసెంబర్ "
    )
    var c = Calendar.getInstance()
    var df = SimpleDateFormat("MMMM yyyy")
    var monthcount = 0
    var cal = Calendar.getInstance()
    private val ADMOB_AD_UNIT_ID = "ca-app-pub-1838677544163695/9583700181"

    private var refresh: Button? = null
    private var startVideoAdsMuted: CheckBox? = null
    private var videoStatus: TextView? = null


    private lateinit var binding: FragmentRashiPahlaluBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRashiPahlaluBinding.inflate(layoutInflater, container, false)



        binding!!.ibBack.setOnClickListener {
            HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()

        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment())
                        .commit()
                }
            })


        // Initialize the Mobile Ads SDK.

        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(
//            activity
//        ) { }

//        refresh = binding.root.findViewById<Button>(com.telugupanchangam.telugucalender.R.id.btn_refresh)
//        startVideoAdsMuted = binding.root.findViewById<CheckBox>(com.telugupanchangam.telugucalender.R.id.cb_start_muted)
//        videoStatus = binding.root.findViewById<TextView>(com.telugupanchangam.telugucalender.R.id.tv_video_status)
//
//        refresh!!.setOnClickListener { refreshAd() }
//
//        refreshAd()


        //Date logic goes here
        cal.add(Calendar.MONTH, monthcount)
        val dateFormat = SimpleDateFormat("MMMM yyyy")
        month_year = dateFormat.format(cal.getTime())
        year = cal[Calendar.YEAR].toString()


        binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ " - " + year)
        binding!!.ArrowLeft.setOnClickListener {

            if (getYearNum().equals("2023") && getMonthNum().equals("01")) {

//                cal.add(Calendar.MONTH, monthcount)
//                val dateFormat = SimpleDateFormat("MMMM yyyy")
//                month_year = dateFormat.format(cal.time)
//                year = cal[Calendar.YEAR].toString()


                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ " - " + year)


            } else {

                var dateFormat: Date? = null
                try {
                    dateFormat = df.parse(month_year)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                c.setTime(dateFormat)
                c.add(Calendar.MONTH, -1)


                month_year = df.format(c.getTime())
                year = c[Calendar.YEAR].toString()

                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ " - " + year)


            }


        }

        binding!!.ArrowRight.setOnClickListener {

            if (getYearNum().equals("2025") && getMonthNum().equals("12")) {

                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ " - " + year)
            } else {
                var dateFormat: Date? = null
                try {
                    dateFormat = df.parse(month_year)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                c.time = dateFormat
                c.add(Calendar.MONTH, 1)
                month_year = df.format(c.time)
                year = c[Calendar.YEAR].toString()
                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ " - " + year)


            }

        }




        binding.llAries.setOnClickListener {

            var rashi_id = 1
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llTaurus.setOnClickListener {

            var rashi_id = 2
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llGemini.setOnClickListener {

            var rashi_id = 3
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llCancer.setOnClickListener {

            var rashi_id = 4
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llLeo.setOnClickListener {

            var rashi_id = 5
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llVirgo.setOnClickListener {

            var rashi_id = 6
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llLibra.setOnClickListener {

            var rashi_id = 7
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llScorpio.setOnClickListener {

            var rashi_id = 8
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llSagittarius.setOnClickListener {

            var rashi_id = 9
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llCapricorn.setOnClickListener {

            var rashi_id = 10
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llAquarius.setOnClickListener {

            var rashi_id = 11
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }
        binding.llPisces.setOnClickListener {

            var rashi_id = 12
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id, rashi_year, rashi_month)


        }

        return binding.root
    }

    @SuppressLint("MissingInflatedId")
    private fun showDialog(description: String , raasi: String) {
        val builder = AlertDialog.Builder(
            activity,
            com.telugupanchangam.telugucalender.R.style.CustomAlertDialog
        ).create()
        val view = layoutInflater.inflate(com.telugupanchangam.telugucalender.R.layout.rashi_layout, null)
        val button = view.findViewById<ImageButton>(com.telugupanchangam.telugucalender.R.id.dialogDismiss_button)
        val tvdescription = view.findViewById<TextView>(com.telugupanchangam.telugucalender.R.id.tvdescription)
        val tvdate = view.findViewById<TextView>(com.telugupanchangam.telugucalender.R.id.tvdate)
        val date = binding!!.PresentMonthAndYear.text.toString()
        builder.setView(view)
        button.setOnClickListener { builder.dismiss() }
        tvdescription.setText(description)
        tvdate.setText(date + " ( " + raasi + " ) " )
        builder.show()
        builder.setCanceledOnTouchOutside(true);
    }


    private fun setTeluguMonth(month_year: String): String? {
        val index = month_year.indexOf(' ')
        month = month_year.substring(0, index)
        val p = Arrays.asList(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        ).indexOf(month)
        return montharray[p]
    }


    private fun rasi(rashi_id: Int, rashi_year: String, rashi_month: String) {

        val raasi: String

        if (rashi_id == 1) {
            raasi = "మేషం"
        } else if (rashi_id == 2) {
            raasi = "వృషభం"
        } else if (rashi_id == 3) {
            raasi = "మిథునం"
        } else if (rashi_id == 4) {
            raasi = "కర్కాటకం"
        } else if (rashi_id == 5) {
            raasi = "సింహం"
        } else if (rashi_id == 6) {
            raasi = "కన్య"
        } else if (rashi_id == 7) {
            raasi = "తులారాశి"
        } else if (rashi_id == 8) {
            raasi = "వృశ్చికం"
        } else if (rashi_id == 9) {
            raasi = "ధనుస్సు"
        } else if (rashi_id == 10) {
            raasi = "మకరం"
        } else if (rashi_id == 11) {
            raasi = "కుంభం"
        } else {
            raasi = "మీనం"
        }

            val params = HashMap<String, String>()
            params[com.telugucalendar.telugupanchangamr.helper.Constant.RASHI_ID] = rashi_id.toString()
            params[com.telugucalendar.telugupanchangamr.helper.Constant.YEAR] = rashi_year
            params[com.telugucalendar.telugupanchangamr.helper.Constant.MONTH] = rashi_month
            com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->
                if (result) {
                    try {
                        val jsonObject = JSONObject(response)
                        if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {

                            Log.d("Rasho", response)
                            val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                            val g = Gson()


                            for (i in 0 until jsonArray.length()) {
                                val jsonObject1 = jsonArray.getJSONObject(i)
                                if (jsonObject1 != null) {

                                    val description: String =
                                        jsonArray.getJSONObject(0).getString(com.telugucalendar.telugupanchangamr.helper.Constant.DESCRIPTION)
                                    val rashi_id: String =
                                        jsonArray.getJSONObject(0).getString(com.telugucalendar.telugupanchangamr.helper.Constant.RASHI_ID)
                                    val title: String =
                                        jsonArray.getJSONObject(0).getString(com.telugucalendar.telugupanchangamr.helper.Constant.RASHI_ID)

                                    showDialog(description,raasi)


                                } else {
                                    Toast.makeText(activity, "No Data", Toast.LENGTH_SHORT).show()
                                    break
                                }
                            }
//                        val adapter = BallisastramAdapter(requireActivity(), balliData)
//                        binding!!.recyclerView.setAdapter(adapter)
                        } else {
                            Toast.makeText(
                                activity,
                                jsonObject.getString(com.telugucalendar.telugupanchangamr.helper.Constant.MESSAGE),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.RASI_LIST, params, true)

        }


        private fun getMonthNum(): String? {
            var newDate: Date? = null
            try {
                newDate = df.parse("" + month_year)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val format = SimpleDateFormat("MM")
            return format.format(newDate)
        }

        private fun getYearNum(): String? {
            var newDate: Date? = null
            try {
                newDate = df.parse("" + month_year)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val format = SimpleDateFormat("yyyy")
            return format.format(newDate)
        }

//
//    private fun refreshAd() {
//        refresh!!.isEnabled = false
//        val builder: AdLoader.Builder = AdLoader.Builder(requireActivity(),ADMOB_AD_UNIT_ID)
//        builder.forUnifiedNativeAd(
//            OnUnifiedNativeAdLoadedListener { unifiedNativeAd ->
//                // OnUnifiedNativeAdLoadedListener implementation.
//                // If this callback occurs after the activity is destroyed, you must call
//                // destroy and return or you may get a memory leak.
//                var isDestroyed: Boolean = false
//                try {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                        isDestroyed = isDestroyed
//                    }
//                    if (isDestroyed || requireActivity().isFinishing() || requireActivity().isChangingConfigurations()) {
//                        unifiedNativeAd.destroy()
//                        return@OnUnifiedNativeAdLoadedListener
//                    }
//                    if (nativeAd != null) {
//                        nativeAd!!.destroy()
//                    }
//
//                    nativeAd = unifiedNativeAd
//                    val frameLayout: FrameLayout = binding.root.findViewById<FrameLayout>(com.telugupanchangam.telugucalender.R.id.fl_adplaceholder)
//                    val adView = layoutInflater
//                        .inflate(com.telugupanchangam.telugucalender.R.layout.ad_unified, null) as UnifiedNativeAdView
//                    populateUnifiedNativeAdView(unifiedNativeAd, adView)
//                    frameLayout.removeAllViews()
//                    frameLayout.addView(adView)
//                } catch (e: Exception) {
//                    Log.e("ERROR", "An error occurred while destroying the ad: ${e.message}")
//                }
//
//
//            })
//        val videoOptions =
//            VideoOptions.Builder().setStartMuted(startVideoAdsMuted!!.isChecked).build()
//        val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
//        builder.withNativeAdOptions(adOptions)
//        val adLoader = builder
//            .withAdListener(
//                object : AdListener() {
//                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                        refresh!!.isEnabled = true
//                        val error = String.format(
//                            "domain: %s, code: %d, message: %s",
//                            loadAdError.domain,
//                            loadAdError.code,
//                            loadAdError.message
//                        )
//                        Toast.makeText(
//                            requireActivity(),
//                            "Failed to load native ad with error $error",
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//                    }
//                })
//            .build()
//        adLoader.loadAd(AdRequest.Builder().build())
//        videoStatus!!.text = ""
//    }





    }