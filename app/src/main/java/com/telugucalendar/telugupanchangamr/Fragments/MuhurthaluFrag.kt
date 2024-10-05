package com.telugucalendar.telugupanchangamr.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.MuhurthaluAdapter
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentMuhurthaluBinding
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MuhurthaluFrag : Fragment() {


    var month_year = ""
    var select_option = "1"
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
    var databaseHelper: com.telugucalendar.telugupanchangamr.helper.DatabaseHelper? = null



    private lateinit var binding: FragmentMuhurthaluBinding


    // This is the part of BottomNavigation
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMuhurthaluBinding.inflate(layoutInflater, container, false)


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


//        mAdView = binding.root.findViewById<AdView>(R.id.adView1)
//        mAdView2 = binding.root.findViewById<AdView>(R.id.adView2)
//        val adRequest = AdRequest.Builder().build()
//        mAdView!!.loadAd(adRequest)
//        mAdView2!!.loadAd(adRequest)

        //Date logic goes here
        cal.add(Calendar.MONTH, monthcount)
        val dateFormat = SimpleDateFormat("MMMM yyyy")
        month_year = dateFormat.format(cal.getTime())
        year = cal[Calendar.YEAR].toString()
        databaseHelper = com.telugucalendar.telugupanchangamr.helper.DatabaseHelper(activity)


        binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year) + year)
        binding!!.ArrowLeft.setOnClickListener {


            if (getYearNum().equals("2023") && getMonthNum().equals("01")) {

                cal.add(Calendar.MONTH, monthcount)
                val dateFormat = SimpleDateFormat("MMMM yyyy")
                month_year = dateFormat.format(cal.time)
                year = cal[Calendar.YEAR].toString()




                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year) + year)


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

                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year) + year)


                muhurthamTabList(select_option, getMonthNum()!!, getYearNum()!!)

            }


        }
        binding!!.ArrowRight.setOnClickListener {


            if (getYearNum().equals("2025") && getMonthNum().equals("12")) {

                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year) + year)
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
                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year) + year)

                muhurthamTabList(select_option, getMonthNum()!!, getYearNum()!!)


            }


        }



        binding.MuhurthaluRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.OptionOne.setOnClickListener {
            unselectall();
            binding.OptionOne.strokeWidth = 4
            select_option = "1"
            binding.MuhurthaluRecycler.visibility = View.GONE
            muhurthamTabList(select_option, getMonthNum()!!, getYearNum()!!)


        }
        binding.OptionTwo.setOnClickListener {
            unselectall();
            binding.OptionTwo.strokeWidth = 4
            select_option = "2"
            binding.MuhurthaluRecycler.visibility = View.GONE
            muhurthamTabList(select_option, getMonthNum()!!, getYearNum()!!)
        }
        binding.OptionThree.setOnClickListener {
            unselectall();
            binding.OptionThree.strokeWidth = 4
            select_option = "3"
            binding.MuhurthaluRecycler.visibility = View.GONE
            muhurthamTabList(select_option, getMonthNum()!!, getYearNum()!!)
        }
        binding.OptionFour.setOnClickListener {
            unselectall();
            binding.OptionFour.strokeWidth = 4
            select_option = "4"
            binding.MuhurthaluRecycler.visibility = View.GONE
            muhurthamTabList(select_option, getMonthNum()!!, getYearNum()!!)
        }

        muhurthamTabList(select_option, getMonthNum()!!, getYearNum()!!)

        //Log.d("MURUTHAM_SIZE", databaseHelper!!.getMuhurthamTabList("1","01","2023").size.toString())

        return binding.root
    }

    private fun unselectall() {
        binding.OptionOne.strokeWidth = 0
        binding.OptionTwo.strokeWidth = 0
        binding.OptionThree.strokeWidth = 0
        binding.OptionFour.strokeWidth = 0
    }

//    private fun muhurthamTabList(mid: String, monthNum: String, yearNum: String) {
////        Log.d("MURUTHAM_LIST_", select_option + " - " + getMonthNum()!! + " - " + getYearNum()!!)
////
////        if (databaseHelper!!.getMuhurthamTabList(mid, monthNum, yearNum).size != 0) {
////            binding.MuhurthaluRecycler!!.visibility = View.VISIBLE
////            MuhurthaluAdapter = MuhurthaluAdapter(
////                databaseHelper!!.getMuhurthamTabList(mid, monthNum, yearNum)
////            )
////            binding.MuhurthaluRecycler!!.adapter = MuhurthaluAdapter
////        } else {
////            binding.MuhurthaluRecycler!!.visibility = View.GONE
////        }
//    }


    private fun muhurthamTabList(mid: String, monthNum: String, yearNum: String) {

        val month: String

        if (monthNum == "01") {
            month = "january"
        } else if (monthNum == "02") {
            month = "february"
        } else if (monthNum == "03") {
            month = "march"
        } else if (monthNum == "04") {
            month = "april"
        } else if (monthNum == "05") {
            month = "may"
        } else if (monthNum == "06") {
            month = "june"
        } else if (monthNum == "07") {
            month = "july"
        } else if (monthNum == "08") {
            month = "august"
        } else if (monthNum == "09") {
            month = "september"
        } else if (monthNum == "10") {
            month = "october"
        } else if (monthNum == "11") {
            month = "november"
        } else if (monthNum == "12") {
            month = "december"
        } else {
            month = "january"
        }


        val params = HashMap<String, String>()
        params["muhurtham_id"] = mid
        params["month"] = month
        params["year"] = yearNum
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->

            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {

                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                        val g = Gson()
                        val muhurthamTab: ArrayList<com.telugucalendar.telugupanchangamr.Model.MuhurthamTab> = ArrayList()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {
                                val group: com.telugucalendar.telugupanchangamr.Model.MuhurthamTab =
                                    g.fromJson(jsonObject1.toString(), com.telugucalendar.telugupanchangamr.Model.MuhurthamTab::class.java)
                                muhurthamTab.add(group)
                            } else {
                                break
                            }
                        }

                        binding.MuhurthaluRecycler.visibility = View.VISIBLE
                        val adapter = MuhurthaluAdapter(muhurthamTab)
                        binding!!.MuhurthaluRecycler.setAdapter(adapter)

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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.MUHURTHAM_LIST_URL, params, true)


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


    private fun setTeluguMonth(month_year: String): String? {
        val index = month_year.indexOf(' ')
        val month = month_year.substring(0, index)
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
}