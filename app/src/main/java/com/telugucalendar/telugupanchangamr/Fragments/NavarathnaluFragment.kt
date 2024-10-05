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
import com.telugucalendar.telugupanchangamr.Adapters.GrahaluAdapter
import com.telugucalendar.telugupanchangamr.Model.Grahalu
import com.telugucalendar.telugupanchangamr.helper.ApiConfig
import com.telugucalendar.telugupanchangamr.helper.Constant
import com.telugucalendar.telugupanchangamr.helper.Session
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentGrahanaluBinding
import com.telugupanchangam.telugucalender.databinding.FragmentNavagrahluBinding
import com.telugupanchangam.telugucalender.databinding.FragmentNavarathnaluBinding
import org.json.JSONArray
import org.json.JSONObject


class NavarathnaluFragment : Fragment() {

    private var binding : FragmentNavarathnaluBinding? = null;
    var activity: Activity? = null
    var session: Session? = null


    var adapter: GrahaluAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavarathnaluBinding.inflate(layoutInflater,container,false)
        activity = getActivity()
        session = Session(activity)


        binding!!.imgBack.setOnClickListener(View.OnClickListener {
            HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
        })

        binding!!.recyclerView.setLayoutManager(
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false
            )
        )


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {



                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
                }
            })


        grahalu()


        return binding!!.root

    }
    private fun grahalu() {
        val params = HashMap<String, String>()
        ApiConfig.RequestToVolley({ result, response ->
            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        binding!!.recyclerView.setVisibility(View.VISIBLE)
                        Log.e("Shasti", response)
                        val jsonArray: JSONArray = jsonObject.getJSONArray(Constant.DATA)

                        val title = jsonArray.getJSONObject(0).getString("title")
                        binding!!.tvTitle.setText(title)
                        val description = jsonArray.getJSONObject(0).getString("description")
                        binding!!.tvDescription.setText(description)

                        val jsonarray2 = jsonArray.getJSONObject(0)
                        val files = jsonarray2.getJSONArray(Constant.NAVARATHNALU_TAB_VARIANT)
                        val g = Gson()
                        val grahalu: ArrayList<Grahalu> = ArrayList<Grahalu>()
                        for (i in 0 until files.length()) {
                            val jsonObject1 = files.getJSONObject(i)
                            if (jsonObject1 != null) {
                                Log.d("Varine", jsonObject1.toString())
                                val group: Grahalu =
                                    g.fromJson(jsonObject1.toString(), Grahalu::class.java)
                                grahalu.add(group)
                            } else {
                                break
                            }
                        }
                        adapter = getActivity()?.let { GrahaluAdapter(it, grahalu) }
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
        }, activity, Constant.NAVARATHNALU_LIST, params, true)
    }


}
