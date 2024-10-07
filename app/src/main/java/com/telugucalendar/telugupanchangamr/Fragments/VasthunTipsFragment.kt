package com.telugucalendar.telugupanchangamr.Fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.helper.Session
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentPushkaraluBinding
import com.telugupanchangam.telugucalender.databinding.FragmentVasthunTipsBinding
import org.json.JSONObject


class VasthunTipsFragment : Fragment() {


    private var binding : FragmentVasthunTipsBinding? = null;
    var activity: Activity? = null
    var session: Session? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVasthunTipsBinding.inflate(layoutInflater,container,false)
        activity = getActivity()
        session = Session(activity)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment())
                        .commit()
                }
            })

        binding!!.ibBack.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, MainFragment())?.commit()
        }

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

                        // Loop through each item in the data array
                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val id = dataObject.getString("id")
                            val title = dataObject.getString("title")
                            val description = dataObject.getString("description")

                            binding!!.tvTitle.text = title

                            binding!!.web


                            binding!!.web.setVerticalScrollBarEnabled(true)

                            binding!!.web.loadDataWithBaseURL("file:///android_asset/fonts/", "<html>\n" +
                                    "<head>\n" +
                                    "<link rel='stylesheet' type='text/css' href='file:///android_asset/fonts/ramabhadra.css'>" +
                                    "<style>\n" +
                                    "body {\n" +
                                    "    font-family: 'ramabhadra';\n" +
                                    "    text-align: center;\n" + // Center-align the text horizontally
                                    "}\n" +
                                    "</style>" +
                                    "</head>" +
                                    "<body>\n" +
                                    description +
                                    "</body>\n" +
                                    "</html>", "text/html", "UTF-8", "");

                            binding!!.web.getSettings().setJavaScriptEnabled(true);
                            binding!!.web.getSettings().setLoadWithOverviewMode(true);

                            // You can now use this data to populate a RecyclerView or any other UI component
                            // For example, you can add it to a list and notify your adapter
                            // Example: dataList.add(DataModel(id, title, description))
                            // adapter.notifyDataSetChanged()
                        }
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
        }, activity, com.telugucalendar.telugupanchangamr.helper.Constant.VASTHU_TIPS_LIST, params, true)
    }



}