package com.greymatter.telugucalender.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greymatter.telugucalender.Adapters.PandugaluAdapter
import com.greymatter.telugucalender.Model.PandugaluModel
import com.greymatter.telugucalender.databinding.FragmentPandugaluBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PandugaluFrag : Fragment() {
    private var binding : FragmentPandugaluBinding? = null;
    private lateinit var PandugaluAdapter : PandugaluAdapter
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


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPandugaluBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment





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

        //Recycler things goes here
        PandugaluAdapter = PandugaluAdapter(recyclerViewData())
        binding?.let {
            it.PandugaluRecycler.layoutManager = LinearLayoutManager(requireContext())
            it.PandugaluRecycler.adapter = PandugaluAdapter
        }
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

    private fun recyclerViewData() : ArrayList<PandugaluModel> {
       val data : ArrayList<PandugaluModel> = arrayListOf()
        data.add(PandugaluModel("12-11-2022","Event One"))
        data.add(PandugaluModel("13-11-2022","Event Two"))
        data.add(PandugaluModel("14-11-2022","Event Three"))
        data.add(PandugaluModel("15-11-2022","Event Four"))
        data.add(PandugaluModel("16-11-2022","Event Five"))
        data.add(PandugaluModel("17-11-2022","Event Six"))
       return data
    }
}