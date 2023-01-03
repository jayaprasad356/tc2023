package com.greymatter.telugucalender.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greymatter.telugucalender.Adapters.MuhurthaluAdapter
import com.greymatter.telugucalender.Model.MuhurthaluModel
import com.greymatter.telugucalender.databinding.FragmentMuhurthaluBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MuhurthaluFrag : Fragment() {


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



    private lateinit var binding : FragmentMuhurthaluBinding
    private lateinit var dataListOne :ArrayList<MuhurthaluModel>
    private lateinit var dataListTwo :ArrayList<MuhurthaluModel>
    private lateinit var dataListThree :ArrayList<MuhurthaluModel>
    private lateinit var dataListFour :ArrayList<MuhurthaluModel>
    private lateinit var MuhurthaluAdapter : MuhurthaluAdapter

    // This is the part of BottomNavigation
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMuhurthaluBinding.inflate(layoutInflater,container,false)




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









        dataListOne = arrayListOf()
        dataListTwo = arrayListOf()
        dataListThree = arrayListOf()
        dataListFour = arrayListOf()

        with(dataListOne) {
            this.add(MuhurthaluModel("SomeRashiOne","20-11-2022 05:27 AM","20-11-2022 09:27 AM"))
        }
        with(dataListTwo) {
            this.add(MuhurthaluModel("SomeRashiTwo","20-11-2022 05:27 AM","20-11-2022 05:27 AM"))
        }
        with(dataListThree) {
            this.add(MuhurthaluModel("SomeRashiThree","20-11-2022 05:27 AM","20-11-2022 05:27 AM"))
        }
        with(dataListFour) {
            this.add(MuhurthaluModel("SomeRashiFour","20-11-2022 05:27 AM","20-11-2022 05:27 AM"))
        }
        binding.MuhurthaluRecycler.layoutManager = LinearLayoutManager(requireActivity())
        MuhurthaluAdapter = MuhurthaluAdapter(dataListOne)
        binding.MuhurthaluRecycler.adapter = MuhurthaluAdapter
        binding.OptionOne.setOnClickListener {
            MuhurthaluAdapter = MuhurthaluAdapter(dataListOne)
            binding.MuhurthaluRecycler.adapter = MuhurthaluAdapter
            MuhurthaluAdapter.notifyDataSetChanged()
        }
        binding.OptionTwo.setOnClickListener {
            MuhurthaluAdapter = MuhurthaluAdapter(dataListTwo)
            binding.MuhurthaluRecycler.adapter = MuhurthaluAdapter
            MuhurthaluAdapter.notifyDataSetChanged()
        }
        binding.OptionThree.setOnClickListener {
            MuhurthaluAdapter = MuhurthaluAdapter(dataListThree)
            binding.MuhurthaluRecycler.adapter = MuhurthaluAdapter
            MuhurthaluAdapter.notifyDataSetChanged()
        }
        binding.OptionFour.setOnClickListener {
            MuhurthaluAdapter = MuhurthaluAdapter(dataListFour)
            binding.MuhurthaluRecycler.adapter = MuhurthaluAdapter
            MuhurthaluAdapter.notifyDataSetChanged()
        }

        return binding.root
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