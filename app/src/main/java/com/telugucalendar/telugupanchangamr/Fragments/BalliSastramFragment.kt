package com.telugucalendar.telugupanchangamr.Fragments

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
import com.telugucalendar.telugupanchangamr.Adapters.BallisastramAdapter
import com.telugucalendar.telugupanchangamr.Model.Ballisastram
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentBalliSastramBinding
import com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS
import org.json.JSONArray
import org.json.JSONObject

class BalliSastramFragment : Fragment() {
    private var binding : FragmentBalliSastramBinding? = null;
    var databaseHelper: com.telugucalendar.telugupanchangamr.helper.DatabaseHelper? = null
    var select_option = "1"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentBalliSastramBinding.inflate(layoutInflater,container,false)

        databaseHelper = com.telugucalendar.telugupanchangamr.helper.DatabaseHelper(activity)


        binding!!.recyclerView.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )

        ballisastram("1")

        binding!!.imgbackbttn.setOnClickListener {
            HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
                }
            })


        binding!!.OptionOne.setOnClickListener {

            binding!!.tvTitle1.text = "బల్లి శాస్త్రము - పురుషులకు"
            unselectall();
            binding!!.OptionOne.strokeWidth = 4
            select_option = "1"
            ballisastram("1")


        }
        binding!!.OptionTwo.setOnClickListener {
            binding!!.tvTitle1.text = "బల్లి శాస్త్రము - స్త్రీలకు"
            unselectall();
            binding!!.OptionTwo.strokeWidth = 4
            select_option = "2"
            ballisastram("2")
        }


        return binding!!.root



    }


    private fun unselectall() {
        binding!!.OptionOne.strokeWidth = 0
        binding!!.OptionTwo.strokeWidth = 0

    }

    private fun ballisastram(id: String) {

        val Title:String

        if (id == "1") {
            Title = "పురుషులకు"

        } else {
            Title = "స్త్రీలకు"
        }


        val params = HashMap<String, String>()
        params[com.telugucalendar.telugupanchangamr.helper.Constant.TITLE] = Title
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(SUCCESS)) {
                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                        val g = Gson()
                        val balliData: ArrayList<Ballisastram> = ArrayList<Ballisastram>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {
                                val group: Ballisastram =
                                    g.fromJson(jsonObject1.toString(), Ballisastram::class.java)
                                balliData.add(group)
                            } else {
                                break
                            }
                        }
                        val adapter = BallisastramAdapter(requireActivity(), balliData)
                        binding!!.recyclerView.setAdapter(adapter)
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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.BALLI_SASTHRAM_LIST_URL, params, true)

    }
}