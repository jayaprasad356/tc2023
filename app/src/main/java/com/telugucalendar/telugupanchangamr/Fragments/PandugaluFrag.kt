package com.telugucalendar.telugupanchangamr.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.FestivalAdapter
import com.telugucalendar.telugupanchangamr.Model.Festival
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentPandugaluBinding
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Date

class PandugaluFrag : Fragment() {
    private var binding : FragmentPandugaluBinding? = null;
    var imgLeft: ImageView? = null
    var imgRight: ImageView? = null
    var tvMonthYear: TextView? = null
    lateinit var month_year : String
    var monthcount = 0
    var cal = Calendar.getInstance()
    var df = SimpleDateFormat("MMMM yyyy")
    var c = Calendar.getInstance()
    var databaseHelper: com.telugucalendar.telugupanchangamr.helper.DatabaseHelper? = null
    var activity: Activity? = null
    var festivalAdapter: FestivalAdapter? = null
    var year =""
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


    @SuppressLint("SimpleDateFormat", "MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment
        binding = FragmentPandugaluBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment





        activity = getActivity()

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



        databaseHelper = com.telugucalendar.telugupanchangamr.helper.DatabaseHelper(activity)




        binding!!.recyclerView.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )


        cal.add(Calendar.MONTH, monthcount)
        val dateFormat = SimpleDateFormat("MMMM yyyy")
        month_year = dateFormat.format(cal.time)
        year = cal[Calendar.YEAR].toString()




        binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ year)
        binding!!.ArrowLeft.setOnClickListener {

            if (getYearNum().equals("2023")&&getMonthNum().equals("01")){

                cal.add(Calendar.MONTH, monthcount)
                val dateFormat = SimpleDateFormat("MMMM yyyy")
                month_year = dateFormat.format(cal.time)
                year = cal[Calendar.YEAR].toString()




                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ year)


              //  festivalList(getMonthNum()!!, getYearNum()!!)
                templelist(getMonthNum()!!, getYearNum()!!)

            }


           else{

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

                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ year)

//                festivalList(getMonthNum()!!, getYearNum()!!)
                templelist(getMonthNum()!!, getYearNum()!!)

           }


        }

        binding!!.ArrowRight.setOnClickListener {

            if (getYearNum().equals("2025")&&getMonthNum().equals("12")){

                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ year)

            }
            else{
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
                binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ year)



//                festivalList(getMonthNum()!!, getYearNum()!!)
                templelist(getMonthNum()!!, getYearNum()!!)

            }

        }


   //  festivalList(getMonthNum()!!, getYearNum()!!)

        templelist(getMonthNum()!!, getYearNum()!!)



        return binding!!.root
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


    private fun festivalList(monthNum: String, yearNum: String) {
        if (databaseHelper!!.getmodelFestivalList(monthNum, yearNum).size !== 0) {
          //  Log.d("festival", databaseHelper!!.getmodelFestivalList(monthNum, yearNum).toString())
            festivalAdapter = FestivalAdapter(requireActivity(), databaseHelper!!.getmodelFestivalList(monthNum, yearNum)
            )
            binding!!.recyclerView!!.adapter = festivalAdapter
        } else {
            Toast.makeText(activity,"No Data",Toast.LENGTH_SHORT).show()
            binding!!.recyclerView!!.visibility = View.GONE
        }
    }





    private fun templelist(monthNum: String, yearNum: String) {

        val month:String

        if (monthNum == "01"){
            month = "january"
        }
        else if (monthNum == "02"){
            month = "february"
        }
        else if (monthNum == "03"){
            month = "march"
        }
        else if (monthNum == "04"){
            month = "april"
        }
        else if (monthNum == "05"){
            month = "may"
        }
        else if (monthNum == "06"){
            month = "june"
        }
        else if (monthNum == "07"){
            month = "july"
        }
        else if (monthNum == "08"){
            month = "august"
        }
        else if (monthNum == "09"){
            month = "september"
        }
        else if (monthNum == "10"){
            month = "october"
        }
        else if (monthNum == "11"){
            month = "november"
        }
        else if (monthNum == "12"){
            month = "december"
        }
        else{
            month = "january"
        }




        val params = HashMap<String, String>()
        params["month"] = month.toString()
        params["year"] = yearNum
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->

            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {

                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                        val g = Gson()
                        val festival: ArrayList<Festival> = ArrayList<Festival>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {
                                val group: Festival =
                                    g.fromJson(jsonObject1.toString(), Festival::class.java)
                                festival.add(group)
                            } else {
                                break
                            }
                        }
                        val adapter = FestivalAdapter(requireActivity(), festival)
                        binding!!.recyclerView.setAdapter(adapter)
                    } else {
                        Toast.makeText(activity,
                            jsonObject.getString(com.telugucalendar.telugupanchangamr.helper.Constant.MESSAGE),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.FESTIVALS_LIST_URL, params, true)

    }





}