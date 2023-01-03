package com.greymatter.telugucalender.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.greymatter.telugucalender.Activites.HomeActivity
import com.greymatter.telugucalender.R
import com.greymatter.telugucalender.databinding.FragmentMoreOptionsBinding

class MoreOptionsFrag : Fragment() {

    private lateinit var binding : FragmentMoreOptionsBinding
    // This is the part of BottomNavigation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(HomeActivity.navbar!!.visibility == View.GONE) {
            HomeActivity.navbar!!.visibility = View.VISIBLE
        }
        // Inflate the layout for this fragment
        binding = FragmentMoreOptionsBinding.inflate(layoutInflater,container,false)
        binding.cvBalliSastram.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container,BalliSastramFragment())
                ?.addToBackStack("null")?.commit()
        }
        binding.CvNamaKaranam.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container,NamaKaranamFrag())
                ?.addToBackStack("null")?.commit()
        }
        binding.CvPadhaDosa.setOnClickListener {
            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container,PadhaDoshaFarg())
                ?.addToBackStack("null")?.commit()
        }
        binding.CvMoreApps.setOnClickListener {
            Toast.makeText(requireContext(),"wow",Toast.LENGTH_LONG).show()
        }
        return binding.root
    }
}