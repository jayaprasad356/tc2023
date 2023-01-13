package com.greymatter.telugucalender.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.greymatter.telugucalender.R
import com.greymatter.telugucalender.databinding.FragmentRashiPahlaluBinding
import com.greymatter.telugucalender.helper.ApiConfig
import com.greymatter.telugucalender.helper.Constant
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class RashiPahlaluFrag : Fragment() {

    var month_year =""
    var month =""
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
    var c = Calendar.getInstance()
    var df = SimpleDateFormat("MMMM yyyy")
    var monthcount = 0
    var cal = Calendar.getInstance()



    private lateinit var binding : FragmentRashiPahlaluBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRashiPahlaluBinding.inflate(layoutInflater, container, false)


        //Date logic goes here
        cal.add(Calendar.MONTH, monthcount)
        val dateFormat = SimpleDateFormat("MMMM yyyy")
        month_year = dateFormat.format(cal.getTime())
        year = cal[Calendar.YEAR].toString()


        binding!!.PresentMonthAndYear.setText(setTeluguMonth(month_year)+ year)
        binding!!.ArrowLeft.setOnClickListener {
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
//            festivalList(getMonthNum(), getYearNum())
        }
        binding!!.ArrowRight.setOnClickListener {
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
//            festivalList(getMonthNum(), getYearNum())
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
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llGemini.setOnClickListener {

            var rashi_id = 3
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llCancer.setOnClickListener {

            var rashi_id = 4
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llLeo.setOnClickListener {

            var rashi_id = 5
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llVirgo.setOnClickListener {

            var rashi_id = 6
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llLibra.setOnClickListener {

            var rashi_id = 7
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llScorpio.setOnClickListener {

            var rashi_id = 8
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llSagittarius.setOnClickListener {

            var rashi_id = 9
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llCapricorn.setOnClickListener {

            var rashi_id = 10
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llAquarius.setOnClickListener {

            var rashi_id = 11
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }
        binding.llPisces.setOnClickListener {

            var rashi_id = 12
            var rashi_year = year
            var rashi_month = month
            rasi(rashi_id,rashi_year,rashi_month)


        }

        return binding.root
    }

    @SuppressLint("MissingInflatedId")
    private fun showDialog(description: String) {
        val builder = AlertDialog.Builder(activity,R.style.CustomAlertDialog).create()
        val view = layoutInflater.inflate(R.layout.rashi_layout,null)
        val  button = view.findViewById<ImageButton>(R.id.dialogDismiss_button)
        val  tvdescription = view.findViewById<TextView>(R.id.tvdescription)
        val  tvdate = view.findViewById<TextView>(R.id.tvdate)
        val date = binding!!.PresentMonthAndYear.text.toString()
        builder.setView(view)
        button.setOnClickListener { builder.dismiss() }
        tvdescription.setText(description)
        tvdate.setText(date)
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

        val params = HashMap<String, String>()
        params[Constant.RASHI_ID] = rashi_id.toString()
        params[Constant.YEAR] = rashi_year
        params[Constant.MONTH] = rashi_month
        ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Log.d("Rasho",response)
                        val jsonArray: JSONArray = jsonObject.getJSONArray(Constant.DATA)
                        val g = Gson()


                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {

                                val description: String = jsonArray.getJSONObject(0).getString(Constant.DESCRIPTION)
                                val rashi_id: String = jsonArray.getJSONObject(0).getString(Constant.RASHI_ID)
                                val title: String = jsonArray.getJSONObject(0).getString(Constant.RASHI_ID)

                                showDialog(description)



                            } else {
                                break
                            }
                        }
//                        val adapter = BallisastramAdapter(requireActivity(), balliData)
//                        binding!!.recyclerView.setAdapter(adapter)
                    } else {
                        Toast.makeText(
                            activity,
                            jsonObject.getString(Constant.MESSAGE),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, activity, Constant.RASI_LIST, params, true)

    }

}