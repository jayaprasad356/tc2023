package com.telugucalendar.telugupanchangamr.Fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.telugucalendar.telugupanchangamr.helper.Session
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentBhargavaBinding
import com.telugupanchangam.telugucalender.databinding.FragmentGovidaNamaluBinding


class GovidaNamaluFragment : Fragment() {

    private var binding : FragmentGovidaNamaluBinding? = null;
    var activity: Activity? = null
    var session: Session? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGovidaNamaluBinding.inflate(layoutInflater,container,false)
        activity = getActivity()
        session = Session(activity)

        return binding?.root
    }


}