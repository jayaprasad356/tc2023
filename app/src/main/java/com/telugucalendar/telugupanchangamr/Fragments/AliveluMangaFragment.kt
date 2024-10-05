package com.telugucalendar.telugupanchangamr.Fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.telugucalendar.telugupanchangamr.helper.Session
import com.telugupanchangam.telugucalender.R
import com.telugupanchangam.telugucalender.databinding.FragmentAliveluMangaBinding
import com.telugupanchangam.telugucalender.databinding.FragmentGovidaNamaluBinding


class AliveluMangaFragment : Fragment() {

    private var binding : FragmentAliveluMangaBinding? = null;
    var activity: Activity? = null
    var session: Session? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAliveluMangaBinding.inflate(layoutInflater,container,false)
        activity = getActivity()
        session = Session(activity)

        return binding?.root
    }

}