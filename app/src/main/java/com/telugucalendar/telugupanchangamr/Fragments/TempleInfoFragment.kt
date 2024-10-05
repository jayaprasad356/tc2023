package com.telugucalendar.telugupanchangamr.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugupanchangam.telugucalender.R


class TempleInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_temple_info, container, false)



        var imgbackbttn = root.findViewById<ImageView>(R.id.imgbackbttn);

        imgbackbttn.setOnClickListener(View.OnClickListener {

            HomeActivity.fm!!.beginTransaction().replace(R.id.Container, TempleListFragment()).commit()

        })


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, MainFragment()).commit()
                }
            })


        var tvTemplename = root.findViewById<TextView>(R.id.tvTemplename);
        var tvTempleLocation = root.findViewById<TextView>(R.id.tvTempleLocation);
        var tvTempledescription = root.findViewById<TextView>(R.id.tvTempledescription);
        var imgtemple = root.findViewById<ImageView>(R.id.imgtemple);
        var tvTitle = root.findViewById<TextView>(R.id.tvTitle);

        var Session = com.telugucalendar.telugupanchangamr.helper.Session(requireActivity())

        var name = Session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_NAME)
        var location = Session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_LOCATION)
        var description = Session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_DESCRIPTION)
        var image = Session.getData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_IMAGE)




        tvTemplename.setText(name)
//        tvTitle.setText(name)
        tvTempleLocation.setText(location)
        tvTempledescription.setText(description)
        Glide.with(requireActivity()).load(image).placeholder(R.drawable.temple_img)
            .into(imgtemple)



        return root
    }


}