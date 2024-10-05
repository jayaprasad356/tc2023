package com.telugucalendar.telugupanchangamr.Fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.AudioHomeAdapter
import com.telugucalendar.telugupanchangamr.Adapters.TempleinfoHomeAdapter
import com.telugucalendar.telugupanchangamr.Ads.AdsUtility
import com.telugucalendar.telugupanchangamr.Model.Templeinfo
import com.telugucalendar.telugupanchangamr.bitmapToImageUrl
import com.telugucalendar.telugupanchangamr.convertXmlToBitmap
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentMainBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Timer
import java.util.TimerTask


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var activity: Activity? = null
    lateinit var templeinfoAdapter: TempleinfoHomeAdapter
    lateinit var audioHomeAdapter: AudioHomeAdapter

    lateinit var imageUrl1: String
    lateinit var imageUrl2: String

    lateinit var imageUrlOne: String
    lateinit var imageUrlTwo: String
    lateinit var imageUrlThree: String
    lateinit var linkone: String
    lateinit var linktwo: String
    lateinit var linkthree: String



    private var currentPage = 0
    private val handler = Handler()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        activity = requireActivity()
        AdsUtility.loadInterstitialAd(requireActivity())



        binding.cvShareapp.setOnClickListener {
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType("text/plain")
                .setChooserTitle("Chooser title")
                .setText("ఈ ఉచిత వేద తెలుగు క్యాలెండర్ డౌన్లోడ్ చేసుకోవడానికి క్రింది లింకును క్లిక్ చేయండి !\n"+"https://play.google.com/store/apps/details?id=com.telugucalendar.telugupanchangamr")
                .startChooser()
        }




        val appPackageName = requireActivity().applicationContext.packageName

        binding.cvrate.setOnClickListener {
            try {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$appPackageName")
                )
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                // Handle the case where the Play Store app is not installed
                // or there's no activity to handle the intent
            }
        }

        binding.llGovidaNamalu.setOnClickListener{
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, GovidaNamaluFragment())?.commit()
        }

        binding.llAliveluManga.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, AliveluMangaFragment())?.commit()
        }

        binding.llPushkaralu.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, PushkaraluFragment())?.commit()
        }

        binding.llVasthunTips.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, VasthunTipsFragment())?.commit()
        }

        binding.cvGrahanalu.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, GrahanaluFragment())?.commit()
        }

        binding.cvNavagrahalu.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, NavagrahluFragment())?.commit()
        }

        binding.cvNavarathnalu.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, NavarathnaluFragment())?.commit()
        }

        binding.cvRudhraksha.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, RudhrakhaluFragment())?.commit()
        }

        binding.llGowri1.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, GowriFragment())?.commit()
        }

        binding.llBhargava.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, BhargavaFragment())?.commit()
        }

        binding.llHoroscope.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, HoroscopeFragment())?.commit()
        }



        binding.CvMoreApp.setOnClickListener {
            val developerId = "4927487196914461976"

            val uri = Uri.parse("https://play.google.com/store/apps/dev?id=$developerId")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Handle the case where no activity can handle the intent (e.g., no web browser)
            }
        }



        coverttourl()

        val viewPager: ViewPager = binding.viewPager


        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (currentPage == 5) {
                        currentPage = 0
                    }
                    viewPager.currentItem = currentPage++
                }
            }
        }, 2000, 3000)







        binding!!.ll1.setOnClickListener {



            AdsUtility.loadInterstitialAd(requireActivity())

            if (!AdsUtility.isInterstitialAdLoaded()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container,
                    com.telugucalendar.telugupanchangamr.Fragments.panchangamFrag()
                )?.commit()

                return@setOnClickListener
            }

            // If the ad is loaded, show it and replace the fragment when closed
            AdsUtility.showInterstitialAd(requireActivity()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container,
                    com.telugucalendar.telugupanchangamr.Fragments.panchangamFrag()
                )?.commit()

            }







        }

        binding!!.ll2.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, PandugaluFrag())?.commit()

            AdsUtility.loadInterstitialAd(requireActivity())

            if (!AdsUtility.isInterstitialAdLoaded()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, PandugaluFrag())?.commit()

                return@setOnClickListener
            }

            // If the ad is loaded, show it and replace the fragment when closed
            AdsUtility.showInterstitialAd(requireActivity()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, PandugaluFrag())?.commit()

            }





        }

        binding!!.ll3.setOnClickListener {

            AdsUtility.loadInterstitialAd(requireActivity())

            if (!AdsUtility.isInterstitialAdLoaded()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, MuhurthaluFrag())?.commit()

                return@setOnClickListener
            }

            // If the ad is loaded, show it and replace the fragment when closed
            AdsUtility.showInterstitialAd(requireActivity()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, MuhurthaluFrag())?.commit()

            }


        }

        binding!!.ll4.setOnClickListener {


          //  HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, RashiPahlaluFrag())?.commit()
            // Load the interstitial ad
            AdsUtility.loadInterstitialAd(requireActivity())

            // Replace the fragment immediately if the ad is not loaded
            if (!AdsUtility.isInterstitialAdLoaded()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, RashiPahlaluFrag())?.commit()
                return@setOnClickListener
            }

            // If the ad is loaded, show it and replace the fragment when closed
            AdsUtility.showInterstitialAd(requireActivity()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, RashiPahlaluFrag())?.commit()
            }
        }




        binding.cvBalliSastram.setOnClickListener {



            // Load the interstitial ad
            AdsUtility.loadInterstitialAd(requireActivity())
            // Replace the fragment immediately if the ad is not loaded
            if (!AdsUtility.isInterstitialAdLoaded()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, BalliSastramFragment())
                    ?.commit()
                return@setOnClickListener
            }

            // If the ad is loaded, show it and replace the fragment when closed
            AdsUtility.showInterstitialAd(requireActivity()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, BalliSastramFragment())
                    ?.commit()
            }

//
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show()
//                mInterstitialAd.setAdListener(object : AdListener() {
//                    override fun onAdClosed() {
//                        AdsUtility.loadInterstitialAd(requireActivity())
//                        HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, BalliSastramFragment())
//                            ?.commit()
//
//                    }
//                })
//            } else {
//                AdsUtility.loadInterstitialAd(requireActivity())
//
//                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, BalliSastramFragment())
//                    ?.commit()
//            }


        }

        binding.CvNamaKaranam.setOnClickListener {





            // Load the interstitial ad
            AdsUtility.loadInterstitialAd(requireActivity())
            // Replace the fragment immediately if the ad is not loaded
            if (!AdsUtility.isInterstitialAdLoaded()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, NamaKaranamFrag())
                    ?.commit()
                return@setOnClickListener
            }

            // If the ad is loaded, show it and replace the fragment when closed
            AdsUtility.showInterstitialAd(requireActivity()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, NamaKaranamFrag())
                    ?.commit()
            }




//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show()
//                mInterstitialAd.setAdListener(object : AdListener() {
//                    override fun onAdClosed() {
//                        AdsUtility.loadInterstitialAd(requireActivity())
//                        HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, NamaKaranamFrag())
//                            ?.commit()
//
//                    }
//                })
//            } else {
//                AdsUtility.loadInterstitialAd(requireActivity())
//
//                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, NamaKaranamFrag())
//                    ?.commit()
//            }

        }

        binding.cvSissu.setOnClickListener {




            // Load the interstitial ad
            AdsUtility.loadInterstitialAd(requireActivity())
            // Replace the fragment immediately if the ad is not loaded
            if (!AdsUtility.isInterstitialAdLoaded()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, SissuJanmaFragment())
                    ?.commit()
                return@setOnClickListener
            }

            // If the ad is loaded, show it and replace the fragment when closed
            AdsUtility.showInterstitialAd(requireActivity()) {
                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, SissuJanmaFragment())
                    ?.commit()
            }


//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show()
//                mInterstitialAd.setAdListener(object : AdListener() {
//                    override fun onAdClosed() {
//                        AdsUtility.loadInterstitialAd(requireActivity())
//                        HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, SissuJanmaFragment())
//                            ?.commit()
//
//                    }
//                })
//            } else {
//                AdsUtility.loadInterstitialAd(requireActivity())
//
//                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, SissuJanmaFragment())
//                    ?.commit()
//            }

        }



        binding!!.rcTemple.setLayoutManager(GridLayoutManager(activity, 4))
        binding!!.rcAudio.setLayoutManager(GridLayoutManager(activity, 4))

        templelist()
        audioList()

        imagelist()

        return binding.root


    }


    private fun audioList() {
        val params = HashMap<String, String>()
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {
                        Log.d("AudioList", response)

                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                        val g = Gson()
                        val audio: ArrayList<com.telugucalendar.telugupanchangamr.Model.Audio> = ArrayList<com.telugucalendar.telugupanchangamr.Model.Audio>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {
                                val group: com.telugucalendar.telugupanchangamr.Model.Audio =
                                    g.fromJson(jsonObject1.toString(), com.telugucalendar.telugupanchangamr.Model.Audio::class.java)
                                audio.add(group)
                            } else {
                                break
                            }
                        }
                        val adapter = AudioHomeAdapter(requireActivity(), audio)
                        binding!!.rcAudio.setAdapter(adapter)
                        audioHomeAdapter.notifyDataSetChanged()
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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO_LIST_URL, params, true)


    }


    private fun templelist() {
        Log.d("Templeinfo", com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_LIST)

        val params = HashMap<String, String>()
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->

            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {

                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                        val g = Gson()
                        val templeinfo: ArrayList<Templeinfo> = ArrayList<Templeinfo>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {
                                val group: Templeinfo =
                                    g.fromJson(jsonObject1.toString(), Templeinfo::class.java)
                                templeinfo.add(group)
                            } else {
                                break
                            }
                        }
                        val adapter = TempleinfoHomeAdapter(requireActivity(), templeinfo)
                        binding!!.rcTemple.setAdapter(adapter)
                        templeinfoAdapter.notifyDataSetChanged()
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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_LIST, params, true)

    }


    private fun slideslist() {
        // Initialize the ViewPager.
        val viewPager: ViewPager = binding.viewPager

        // Create an array list for storing dynamic image URLs.
        val sliderDataArrayList: ArrayList<com.telugucalendar.telugupanchangamr.Model.SliderData> = ArrayList()

        // Add empty slides as the first two items
        sliderDataArrayList.add(com.telugucalendar.telugupanchangamr.Model.SliderData(imageUrl1, ""))  // Empty slide 1
        sliderDataArrayList.add(com.telugucalendar.telugupanchangamr.Model.SliderData(imageUrl2, ""))  // Empty slide 2

        // Dynamic image URLs from API
        val apiImageUrl1 = imageUrlOne
        val apiImageUrl2 = imageUrlTwo
        val apiImageUrl3 = imageUrlThree

        // Add dynamic images to the list
        sliderDataArrayList.add(
            com.telugucalendar.telugupanchangamr.Model.SliderData(
                apiImageUrl1,
                linkone
            )
        )
        sliderDataArrayList.add(
            com.telugucalendar.telugupanchangamr.Model.SliderData(
                apiImageUrl2,
                linktwo
            )
        )
        sliderDataArrayList.add(
            com.telugucalendar.telugupanchangamr.Model.SliderData(
                apiImageUrl3,
                linkthree
            )
        )

        // Create a custom adapter with the dynamic data
        val adapter = com.telugucalendar.telugupanchangamr.Adapters.SliderAdapter(
            requireActivity(),
            sliderDataArrayList
        )

        // Set adapter to the ViewPager
        viewPager.adapter = adapter

        // Set other properties as needed
        viewPager.currentItem = 0
    }



    private fun imagelist() {
        val params: Map<String, String> = HashMap()
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {
                        val dataObject = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)

                        // Check if there are at least two items in the array
                        if (dataObject.length() >= 2) {
                            // Extract data for Image One
                            val imageOne = dataObject.getJSONObject(0)
                            val idOne = imageOne.getString("id")
                            val nameOne = imageOne.getString("name")
                            imageUrlOne = imageOne.getString("image")
                            linkone = imageOne.getString("link")


                            Log.d("ImageOne", imageUrlOne)

                            // Extract data for Image Two
                            val imageTwo = dataObject.getJSONObject(1)
                            val idTwo = imageTwo.getString("id")
                            val nameTwo = imageTwo.getString("name")
                            imageUrlTwo = imageTwo.getString("image")
                            linktwo = imageTwo.getString("link")

                            Log.d("ImageTwo", imageUrlTwo)


                            // Extract data for Image Three

                            val imageThree = dataObject.getJSONObject(2)
                            val idThree = imageThree.getString("id")
                            val nameThree = imageThree.getString("name")
                            imageUrlThree = imageThree.getString("image")
                            linkthree = imageThree.getString("link")




                            slideslist()


                        } else {
                            // Handle the case where there are not enough items in the array
                            Toast.makeText(
                                activity,
                                "Not enough items in the array",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "" + jsonObject.getString(com.telugucalendar.telugupanchangamr.helper.Constant.MESSAGE).toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.IMAGE_SLIDER_LIST_URL, params, true)
    }


    private fun coverttourl() {

        val session = com.telugucalendar.telugupanchangamr.helper.Session(activity)

        // Inflate the layout
        val layout1 = LayoutInflater.from(activity).inflate(R.layout.slider1_layout, null)
        val layout2 = LayoutInflater.from(activity).inflate(R.layout.slider2_layout, null)


        val tvDate = layout1.findViewById<TextView>(R.id.tvDate)
        val tvYearName = layout1.findViewById<TextView>(R.id.tvYearName)
        val tvWeekName = layout1.findViewById<TextView>(R.id.tvWeekName)
        val tvMonthName = layout1.findViewById<TextView>(R.id.tvGoodTimings)


        tvYearName.text = session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.YEAR_NAME)
        tvWeekName.text = session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.WEEK_Name)
        tvMonthName.text = session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.GOOD_TIMING)





        tvDate.text = session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.DATE)


        // Get references to TextViews
        val tvSunrise = layout2.findViewById<TextView>(R.id.tvSunrise)
        val tvSunset = layout2.findViewById<TextView>(R.id.tvSunset)
        val tvThidhi = layout2.findViewById<TextView>(R.id.tvThidhi)
        val tvNakshathram = layout2.findViewById<TextView>(R.id.tvNakshathram)

        // Set text to TextViews
        tvSunrise.text = session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.SUNRISE)
        tvSunset.text = session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.SUNSET)
        tvThidhi.text = session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.THIDHI)
        tvNakshathram.text = session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.NAMAKARAM)

        // Convert layout to Bitmap after setting text
        val bitmap2 = convertXmlToBitmap(layout2)
        imageUrl2 = bitmapToImageUrl(bitmap2)

        val bitmap1 = convertXmlToBitmap(layout1)
        imageUrl1 = bitmapToImageUrl(bitmap1)


    }


}