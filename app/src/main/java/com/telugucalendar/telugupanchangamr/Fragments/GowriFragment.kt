package com.telugucalendar.telugupanchangamr.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.GowriAdapter
import com.telugucalendar.telugupanchangamr.helper.DatabaseHelper
import com.telugucalendar.telugupanchangamr.helper.Session
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentGowriBinding
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class GowriFragment : Fragment() {

    private var binding: FragmentGowriBinding? = null
    private var activity: Activity? = null
    private lateinit var session: Session
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var calendar: Calendar
    private lateinit var dateString: String
    private var clickedDay: Int = 0
    private lateinit var dayName: String
    private lateinit var gowriAdapter: GowriAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = FragmentGowriBinding.inflate(layoutInflater, container, false)
        activity = requireActivity()
        session = Session(activity)

        calendar = Calendar.getInstance()
        databaseHelper = DatabaseHelper(requireActivity())

        setupListeners()

        // Get current day details
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = Date()
        dateString = dateFormat.format(date)

        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        dayName = dayFormat.format(date)

        // Set up RecyclerView
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        handleCurrentDay(dayOfWeek)


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment())
                        .commit()
                }
            })

        return binding?.root
    }

    private fun setupListeners() {
        binding?.ibBack?.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()
        }

        binding?.apply {
            tvMonday.setOnClickListener {
                clickedDay = 1
                unselectAllDays()
                tvMonday.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Monday")
            }
            tvTuesday.setOnClickListener {
                clickedDay = 2
                unselectAllDays()
                tvTuesday.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Tuesday")
            }
            tvWednesday.setOnClickListener {
                clickedDay = 3
                unselectAllDays()
                tvWednesday.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Wednesday")
            }
            tvThursday.setOnClickListener {
                clickedDay = 4
                unselectAllDays()
                tvThursday.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Thursday")
            }
            tvFriday.setOnClickListener {
                clickedDay = 5
                unselectAllDays()
                tvFriday.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Friday")
            }
            tvSaturday.setOnClickListener {
                clickedDay = 6
                unselectAllDays()
                tvSaturday.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Saturday")
            }
            tvSunday.setOnClickListener {
                clickedDay = 7
                unselectAllDays()
                tvSunday.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Sunday")
            }
        }
    }

    private fun unselectAllDays() {
        binding?.apply {
            tvMonday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvTuesday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvWednesday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvThursday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvFriday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvSaturday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvSunday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    private fun handleCurrentDay(dayOfWeek: Int) {
        when (dayOfWeek) {
            Calendar.SUNDAY -> {
                unselectAllDays()
                binding?.tvSunday?.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Sunday")
            }
            Calendar.MONDAY -> {
                unselectAllDays()
                binding?.tvMonday?.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Monday")
            }
            Calendar.TUESDAY -> {
                unselectAllDays()
                binding?.tvTuesday?.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Tuesday")
            }
            Calendar.WEDNESDAY -> {
                unselectAllDays()
                binding?.tvWednesday?.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Wednesday")
            }
            Calendar.THURSDAY -> {
                unselectAllDays()
                binding?.tvThursday?.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Thursday")
            }
            Calendar.FRIDAY -> {
                unselectAllDays()
                binding?.tvFriday?.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Friday")
            }
            Calendar.SATURDAY -> {
                unselectAllDays()
                binding?.tvSaturday?.setTextColor(ContextCompat.getColor(requireContext(), R.color.calHeaderT))
                gowriList("Saturday")
            }
        }
    }

    private fun gowriList(day: String) {
        val params = HashMap<String, String>()
        params["day"] = day

        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {
                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                        val gowriTab = ArrayList<com.telugucalendar.telugupanchangamr.Model.Gowri>()
                        val g = Gson()

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            val group: com.telugucalendar.telugupanchangamr.Model.Gowri =
                                g.fromJson(jsonObject1.toString(), com.telugucalendar.telugupanchangamr.Model.Gowri::class.java)
                            gowriTab.add(group)
                        }

                        binding?.recyclerView?.visibility = View.VISIBLE
                        gowriAdapter = GowriAdapter(activity, gowriTab)
                        binding?.recyclerView?.adapter = gowriAdapter

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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.GOWRI_LIST_URL, params, true)
    }
}
