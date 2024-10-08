package com.telugucalendar.telugupanchangamr.Fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.BallisastramAdapter
import com.telugucalendar.telugupanchangamr.Adapters.GrahaluAdapter
import com.telugucalendar.telugupanchangamr.Adapters.PushkaraluAdapter
import com.telugucalendar.telugupanchangamr.Model.Ballisastram
import com.telugucalendar.telugupanchangamr.Model.Grahalu
import com.telugucalendar.telugupanchangamr.Model.Pushkaralu
import com.telugucalendar.telugupanchangamr.helper.ApiConfig
import com.telugucalendar.telugupanchangamr.helper.Constant
import com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS
import com.telugucalendar.telugupanchangamr.helper.Session
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentPushkaraluBinding
import org.json.JSONArray
import org.json.JSONObject

class PushkaraluFragment : Fragment() {

    private var binding : FragmentPushkaraluBinding? = null
    var activity: Activity? = null
    var session: Session? = null
    var pushkaraluList: ArrayList<Pushkaralu>? = null
    var adapter: PushkaraluAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPushkaraluBinding.inflate(layoutInflater, container, false)
        activity = getActivity()
        session = Session(activity)

        // Handle back press
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment())
                        .commit()
                }
            })

        // Set up back button click listener
        binding!!.ibBack.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()
        }

        // Initialize the list and adapter
        pushkaraluList = ArrayList()
        adapter = PushkaraluAdapter(requireActivity(), pushkaraluList!!)

        // Set up RecyclerView
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
        binding?.recyclerView?.adapter = adapter

        // Fetch data
        list()

        return binding?.root
    }

    private fun list() {
        val params = HashMap<String, String>()
        ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(SUCCESS)) {
                        Log.e("Shasti", response)
                        val jsonArray: JSONArray = jsonObject.getJSONArray(Constant.DATA)

                        val title = jsonArray.getJSONObject(0).getString("title")
                        binding!!.tvTitle.setText(title)
                        val description = jsonArray.getJSONObject(0).getString("description")
                       // binding!!.tvDescription.setText(description)

                        val jsonarray2 = jsonArray.getJSONObject(0)
                        val files = jsonarray2.getJSONArray(Constant.PUSHKARALU_VARIANT)
                        val g = Gson()
                        val pushkaralu: ArrayList<Pushkaralu> = ArrayList<Pushkaralu>()
                        for (i in 0 until files.length()) {
                            val jsonObject1 = files.getJSONObject(i)
                            if (jsonObject1 != null) {
                                Log.d("Varine", jsonObject1.toString())
                                val group: Pushkaralu =
                                    g.fromJson(jsonObject1.toString(), Pushkaralu::class.java)
                                pushkaralu.add(group)
                            } else {
                                break
                            }
                        }
                        adapter = getActivity()?.let { PushkaraluAdapter(it, pushkaralu) }
                        binding!!.recyclerView.setAdapter(adapter)
                        //       tvTitle.setText(jsonArray.getJSONObject(0).getString("text1"))
                    } else {
                        binding!!.recyclerView.setVisibility(View.GONE)
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
        }, activity, Constant.PUSHKARALU_LIST, params, true)
    }
}
