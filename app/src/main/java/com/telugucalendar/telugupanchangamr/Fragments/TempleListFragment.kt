package com.telugucalendar.telugupanchangamr.Fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.TempleinfoAdapter
import com.telugucalendar.telugupanchangamr.Model.Templeinfo
import com.telugupanchangam.telugucalender.R
import org.json.JSONArray
import org.json.JSONObject


class TempleListFragment : Fragment() {

    var activity: Activity? = null
    lateinit var recyclerView: RecyclerView
    lateinit var templeinfoAdapter: TempleinfoAdapter
    var audioLiveAdapter: com.telugucalendar.telugupanchangamr.Adapters.AudioLiveAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_temple_list, container, false)


        activity = requireActivity()


        recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)


        var imgbackbttn = root.findViewById<ImageView>(R.id.imgbackbttn);

        imgbackbttn.setOnClickListener(View.OnClickListener {

            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()

        })


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
                }
            })



        recyclerView!!.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))




        templelist()

        return root
    }


    private fun templelist() {
        Log.d("Templeinfo", com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_LIST)

        val params = HashMap<String, String>()
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->

            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {

                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                        val g = Gson()
                        val templeinfo: ArrayList<Templeinfo> = ArrayList<Templeinfo>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {
                                val group: Templeinfo =
                                    g.fromJson(jsonObject1.toString(), Templeinfo::class.java)
                                templeinfo.add(group)
                            } else {
                                break
                            }
                        }
                        val adapter = TempleinfoAdapter(requireActivity(), templeinfo)
                        recyclerView.setAdapter(adapter)
                        templeinfoAdapter.notifyDataSetChanged()




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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_LIST, params, true)


    }



}