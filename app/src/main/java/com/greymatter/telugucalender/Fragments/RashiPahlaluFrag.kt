package com.greymatter.telugucalender.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.greymatter.telugucalender.Activites.HomeActivity
import com.greymatter.telugucalender.R
import com.greymatter.telugucalender.databinding.FragmentMoreOptionsBinding
import com.greymatter.telugucalender.databinding.FragmentRashiPahlaluBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class RashiPahlaluFrag : Fragment() {

    var month_year =""
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

            showDialog();
        }


        return binding.root
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(activity,R.style.CustomAlertDialog).create()
        val view = layoutInflater.inflate(R.layout.rashi_layout,null)
        val  button = view.findViewById<ImageButton>(R.id.dialogDismiss_button)
        builder.setView(view)
        button.setOnClickListener {
            builder.dismiss()
        }
        builder.show()
        builder.setCanceledOnTouchOutside(true);
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