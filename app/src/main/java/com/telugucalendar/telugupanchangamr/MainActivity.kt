package com.telugucalendar.telugupanchangamr

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Adapters.AudioHomeAdapter
import com.telugucalendar.telugupanchangamr.Adapters.TempleinfoHomeAdapter
import com.telugucalendar.telugupanchangamr.Model.Templeinfo
import com.telugupanchangam.telugucalender.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    private var binding: ActivityMainBinding? = null
    private var activity: Activity? = null
    lateinit var templeinfoAdapter: TempleinfoHomeAdapter
    lateinit var audioHomeAdapter: AudioHomeAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        activity = this@MainActivity


        binding!!.ll1.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding!!.ll2.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding!!.ll3.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding!!.ll4.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }



        binding!!.rcTemple.setLayoutManager(GridLayoutManager(activity, 4))
        binding!!.rcAudio.setLayoutManager(GridLayoutManager(activity, 4))

        templelist()
        audioList()

    }

    private fun audioList() {

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
                        val adapter = AudioHomeAdapter(this, audio)
                        binding!!.rcAudio.setAdapter(adapter)
                        audioHomeAdapter.notifyDataSetChanged()
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
                        val adapter = TempleinfoHomeAdapter(this, templeinfo)
                        binding!!.rcTemple.setAdapter(adapter)
                        templeinfoAdapter.notifyDataSetChanged()
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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_LIST, params, true)

    }

}