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
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentAudioListBinding
import org.json.JSONArray
import org.json.JSONObject


class AudioListFragment : Fragment() {


    var databaseHelper: com.telugucalendar.telugupanchangamr.helper.DatabaseHelper? = null
    var activity: Activity? = null
    var session: com.telugucalendar.telugupanchangamr.helper.Session? = null
    var audioLiveAdapter: com.telugucalendar.telugupanchangamr.Adapters.AudioLiveAdapter? = null

    var binding: FragmentAudioListBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioListBinding.inflate(inflater, container, false)


        activity = requireActivity()
        session = com.telugucalendar.telugupanchangamr.helper.Session(activity)


        binding!!.ibBack.setOnClickListener(View.OnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()
        })


        // val gridLayoutManager = GridLayoutManager(activity, 2)
        binding!!.recyclerView!!.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        audiolive()


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
                }
            })

        return binding!!.root





    }


    private fun audiolive() {

        val params = HashMap<String, String>()
        com.telugucalendar.telugupanchangamr.helper.ApiConfig.RequestToVolley({ result, response ->

            if (result) {
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean(com.telugucalendar.telugupanchangamr.helper.Constant.SUCCESS)) {
                        Log.d("AudioList", response)

                        val jsonArray: JSONArray = jsonObject.getJSONArray(com.telugucalendar.telugupanchangamr.helper.Constant.DATA)
                        val g = Gson()
                        val audio: ArrayList<com.telugucalendar.telugupanchangamr.Model.Audio> = ArrayList<com.telugucalendar.telugupanchangamr.Model.Audio>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            if (jsonObject1 != null) {
                                val group: com.telugucalendar.telugupanchangamr.Model.Audio =
                                    g.fromJson(jsonObject1.toString(), com.telugucalendar.telugupanchangamr.Model.Audio::class.java)
                                audio.add(group)
                            } else {
                                break
                            }
                        }
                        val adapter = com.telugucalendar.telugupanchangamr.Adapters.AudioLiveAdapter(
                            requireActivity(),
                            audio
                        )
                        binding!!.recyclerView.setAdapter(adapter)
                    } else {
                        Toast.makeText(activity,
                            jsonObject.getString(com.telugucalendar.telugupanchangamr.helper.Constant.MESSAGE),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO_LIST_URL, params, true)


    }



}