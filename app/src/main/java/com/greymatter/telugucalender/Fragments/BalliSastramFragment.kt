package com.greymatter.telugucalender.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.greymatter.telugucalender.Activites.HomeActivity
import com.greymatter.telugucalender.Adapters.BallisastramAdapter
import com.greymatter.telugucalender.Model.Ballisastram
import com.greymatter.telugucalender.databinding.FragmentPandugaluBinding
import com.greymatter.telugucalender.helper.ApiConfig
import com.greymatter.telugucalender.helper.Constant
import com.greymatter.telugucalender.helper.Constant.SUCCESS
import com.greymatter.telugucalender.helper.DatabaseHelper
import org.json.JSONArray
import org.json.JSONObject

class BalliSastramFragment : Fragment() {
    private var binding : FragmentPandugaluBinding? = null;
    var databaseHelper: DatabaseHelper? = null
    var activity: Activity? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(HomeActivity.navbar!!.visibility == View.GONE) {
            HomeActivity.navbar!!.visibility = View.VISIBLE
        }

        binding = FragmentPandugaluBinding.inflate(layoutInflater,container,false)
        activity = getActivity()

        databaseHelper = DatabaseHelper(activity)


        binding!!.recyclerView.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )

        ballisastram()

        return binding!!.root



    }

    private fun ballisastram() {

        val params = HashMap<String, String>()
        params[Constant.BALLI_SASTHRAM] = "1"
        ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(SUCCESS)) {
                        val jsonArray: JSONArray = jsonObject.getJSONArray(Constant.BALLI_SASTHAM_LIST)
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
                            jsonObject.getString(Constant.MESSAGE),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, activity, Constant.ALLDATALIST_URL, params, true)

    }
}