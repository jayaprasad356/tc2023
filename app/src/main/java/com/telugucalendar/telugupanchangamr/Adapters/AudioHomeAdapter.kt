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
import com.telugucalendar.telugupanchangamr.Fragments.AudioListFragment
import com.telugucalendar.telugupanchangamr.Fragments.AudioPlayFragment
import com.telugupanchangam.telugucalender.R
import java.util.*


class AudioHomeAdapter(val activity: Activity, audio: ArrayList<com.telugucalendar.telugupanchangamr.Model.Audio>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var audio: ArrayList<com.telugucalendar.telugupanchangamr.Model.Audio>

    init {
        this.audio = audio
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.home_templelist_layout, parent, false)
        return ExploreItemHolder(view)
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder = holderParent as ExploreItemHolder
        val audio: com.telugucalendar.telugupanchangamr.Model.Audio = audio[position]


        if (position == 3) {
            holder.imgtemple1.visibility = View.VISIBLE
            holder.imgtemple1.setImageDrawable(activity.getDrawable(R.drawable.more_ic))
            holder.itemView.setOnClickListener(View.OnClickListener setOnClickListener@{


                // Load the interstitial ad
                AdsUtility.loadInterstitialAd(activity)
                // Replace the fragment immediately if the ad is not loaded
                if (!AdsUtility.isInterstitialAdLoaded()) {
                    HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, AudioListFragment())?.commit()
                    return@setOnClickListener
                }

                // If the ad is loaded, show it and replace the fragment when closed
                AdsUtility.showInterstitialAd(activity) {
                    HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, AudioListFragment())?.commit()
                }



        //                AdsUtility.loadInterstitialAd(activity)


                //                if (AdsUtility.mInterstitialAd.isLoaded()) {
        //                    AdsUtility.mInterstitialAd.show()
        //                    AdsUtility.mInterstitialAd.setAdListener(object : AdListener() {
        //                        override fun onAdClosed() {
        //                            AdsUtility.loadInterstitialAd(activity)
        //                            HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, AudioListFragment())?.commit()
        //
        //                        }
        //                    })
        //
        //                }


            })

        }

        else{

            holder.tvTemplename.setText(audio.title)
            holder.tvTempleLocation.setText(audio.lyrics)
            Glide.with(activity).load(audio.image).placeholder(R.drawable.four_img)
                .into(holder.imgtemple)

            holder.itemView.setOnClickListener(View.OnClickListener {

                if (position == 3) {
                    HomeActivity.fm?.beginTransaction()?.replace(R.id.Container, AudioListFragment())?.commit()

                }
                else{

                    val session = com.telugucalendar.telugupanchangamr.helper.Session(activity)


                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO_TITLE, audio.getTitle())
                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO_IMAGE, audio.getImage())
                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO, audio.getAudio())
                    session.setData(com.telugucalendar.telugupanchangamr.helper.Constant.LYRICS, audio.getLyrics())


                    HomeActivity.fm!!.beginTransaction()
                        .replace(R.id.Container, AudioPlayFragment()).commit()

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