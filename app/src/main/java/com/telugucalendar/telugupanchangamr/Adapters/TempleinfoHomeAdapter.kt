package com.telugucalendar.telugupanchangamr.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugucalendar.telugupanchangamr.Ads.AdsUtility
import com.telugucalendar.telugupanchangamr.Fragments.TempleInfoFragment
import com.telugucalendar.telugupanchangamr.Fragments.TempleListFragment
import com.telugucalendar.telugupanchangamr.Model.Templeinfo
import com.telugupanchangam.telugucalender.R
import java.util.*


class TempleinfoHomeAdapter(val activity: Activity, templeinfo: ArrayList<Templeinfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var templeinfo: ArrayList<Templeinfo>

    init {
        this.templeinfo = templeinfo
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.home_templelist_layout, parent, false)
        return ExploreItemHolder(view)
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder = holderParent as ExploreItemHolder
        val templeinfo: Templeinfo = templeinfo[position]


        if (position == 3) {
            holder.imgtemple1.visibility = View.VISIBLE
            holder.imgtemple1.setImageDrawable(activity.getDrawable(R.drawable.more_ic))
            holder.itemView.setOnClickListener(View.OnClickListener setOnClickListener@{


                // Load the interstitial ad
                AdsUtility.loadInterstitialAd(activity)
                // Replace the fragment immediately if the ad is not loaded
                if (!AdsUtility.isInterstitialAdLoaded()) {
                    HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, TempleListFragment())?.commit()
                    return@setOnClickListener
                }

                // If the ad is loaded, show it and replace the fragment when closed
                AdsUtility.showInterstitialAd(activity) {
                    HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, TempleListFragment())?.commit()
                }


                //   AdsUtility.loadInterstitialAd(activity)
//                HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, TempleListFragment())?.commit()

        //                if (AdsUtility.mInterstitialAd.isLoaded()) {
        //                    AdsUtility.mInterstitialAd.show()
        //                    AdsUtility.mInterstitialAd.setAdListener(object : AdListener() {
        //                        override fun onAdClosed() {
        //                            AdsUtility.loadInterstitialAd(activity)
        //                            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, TempleListFragment())?.commit()
        //
        //                        }
        //                    })
        //
        //
        //
        //                }

        //                else {
        //                    AdsUtility.loadInterstitialAd(activity)
        //
        //                    HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, TempleListFragment())?.commit()
        //
        //                }


            })

        }

        else{

            holder.tvTemplename.setText(templeinfo.name)
            holder.tvTempleLocation.setText(templeinfo.location)
            Glide.with(activity).load(templeinfo.image).placeholder(R.drawable.four_img)
                .into(holder.imgtemple)

            holder.itemView.setOnClickListener(View.OnClickListener {

                if (position == 3) {
                    HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, TempleListFragment())?.commit()
                }
                else{

                    val session = com.telugucalendar.telugupanchangamr.helper.Session(activity)


                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_ID, templeinfo.id)
                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_NAME, templeinfo.name)
                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_LOCATION, templeinfo.location)
                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_DESCRIPTION, templeinfo.description)
                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.TEMPLEINFO_IMAGE, templeinfo.image)


                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, TempleInfoFragment()).commit()
                }
//                val intent = Intent(activity, TempleinfoActivity::class.java)
//                intent.putExtra(Constant.TEMPLEINFO_ID, templeinfo.id)
//                intent.putExtra(Constant.TEMPLEINFO_NAME, templeinfo.name)
//                intent.putExtra(Constant.TEMPLEINFO_LOCATION, templeinfo.location)
//                intent.putExtra(Constant.TEMPLEINFO_DESCRIPTION, templeinfo.description)
//                intent.putExtra(Constant.TEMPLEINFO_IMAGE, templeinfo.image)
//                activity.startActivity(intent)

            })

        }






    }

    override fun getItemCount(): Int {
        return 4
    }

    internal class ExploreItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imgtemple: ImageView
        val imgtemple1: ImageView
        val tvTemplename: TextView
        val tvTempleLocation: TextView

        init {
            imgtemple = itemView.findViewById(R.id.imgtemple)
            tvTempleLocation = itemView.findViewById(R.id.tvTempleLocation)
            tvTemplename = itemView.findViewById(R.id.tvTemplename)
            imgtemple1 = itemView.findViewById(R.id.imgtemple1)
        }
    }
}