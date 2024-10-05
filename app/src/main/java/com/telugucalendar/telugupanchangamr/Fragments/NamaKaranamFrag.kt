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
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.NamakaranamAdapter
import com.telugucalendar.telugupanchangamr.Model.Namakaranam
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentNamaKaranamBinding
import org.json.JSONArray
import org.json.JSONObject

class NamaKaranamFrag : Fragment() {

    private var binding : FragmentNamaKaranamBinding? = null;
    var databaseHelper: com.telugucalendar.telugupanchangamr.helper.DatabaseHelper? = null
    var activity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = com.telugupanchangam.telugucalender.databinding.FragmentNamaKaranamBinding.inflate(layoutInflater,container,false)
        activity = getActivity()

        databaseHelper = com.telugucalendar.telugupanchangamr.helper.DatabaseHelper(activity)


        binding!!.imgbackbttn.setOnClickListener(View.OnClickListener {
            HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
        })



        binding!!.recyclerView.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )

        namakaranam();

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {



                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
                }
            })

        return binding!!.root

    }


    private fun namakaranam() {
        val params = HashMap<String, String>()
        params[com.telugucalendar.telugupanchangamr.helper.Constant.NAMAKARAM] = "1"
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {
                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.NAMAKARAM_LIST)
                        val g = Gson()
                        val namakaranam: ArrayList<Namakaranam> = ArrayList<Namakaranam>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {
                                val group: Namakaranam =
                                    g.fromJson(jsonObject1.toString(), Namakaranam::class.java)
                                namakaranam.add(group)
                            } else {
                                break
                            }
                        }
                        val adapter = NamakaranamAdapter(requireActivity(),namakaranam)
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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.ALLDATALIST_URL, params, true)


    }
}