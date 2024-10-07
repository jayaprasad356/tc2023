package com.telugucalendar.telugupanchangamr.Fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.PushkaraluAdapter
import com.telugucalendar.telugupanchangamr.Model.Pushkaralu
import com.telugucalendar.telugupanchangamr.helper.Session
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentPushkaraluBinding
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
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {
                        val dataArray = jsonObject.getJSONArray("data")


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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.PUSHKARALU_LIST, params, true)
    }
}
