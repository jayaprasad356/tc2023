package com.telugucalendar.telugupanchangamr.Fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity
import com.telugupanchangam.telugucalender.R
import org.json.JSONObject
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

class AudioPlayFragment() : Fragment() {

    var hfmPlayer: MediaPlayer? = null
    private var playIcon: ImageButton? = null
    private var forward: ImageButton? = null
    private var backward: ImageButton? = null
    private var imgLoop: ImageView? = null
    private var mSeekBar: SeekBar? = null
    private var totTime: TextView? = null
    private var curTime: TextView? = null
    private var tvTitle: TextView? = null
    private var tvTitle1: TextView? = null
    private var count = 1
    private val setcount = 0
    private val seekForwardTime = 10 * 1000 // default 5 seconds
    private val seekBackwardTime = 10 * 1000 // default 5 seconds
    private var Title: String? = null
    private var timer: String? = null
    private var lyricsText: TextView? = null
    private var isPlaying = false
    private var jsonload = false
    private var splandata: JSONObject? = null
    private var lyricsData: String? = null
    private var Audio: String? = null
    private var Lyrics: String? = null
    private var Image: String? = null
    private var mediaPlayer: MediaPlayer? = null
    private var imgBack: ImageView? = null
    private var sharedpreferences: SharedPreferences? = null
    private var loading: CircularProgressIndicator? = null
    private var ivimage: ImageView? = null
    private var tvLyrics: TextView? = null
    private var loop = false
    private val chalisatime = arrayOf(
        "0:00", "0:23", "0:55", "1:17", "1:44", "2:06", "2:30", "2:53", "3:18", "3:40",
        "4:06", "4:28", "4:54", "5:16", "5:42", "6:04", "6:29", "6:52", "7:17", "7:39",
        "8:04", "8:26", "9:00"
    )

    private var session: com.telugucalendar.telugupanchangamr.helper.Session? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_audio_play, container, false)

        session = com.telugucalendar.telugupanchangamr.helper.Session(requireActivity())

        curTime = root.findViewById(R.id.currentState)
        ivimage = root.findViewById(R.id.ivimage)
        totTime = root.findViewById(R.id.totalValue)
        mSeekBar = root.findViewById(R.id.mySeekbar)
        playIcon = root.findViewById(R.id.playBtn)
        imgLoop = root.findViewById(R.id.imgLoop)
        forward = root.findViewById(R.id.forward)
        backward = root.findViewById(R.id.backward)
        tvTitle = root.findViewById(R.id.tvTitle)
        tvTitle1 = root.findViewById(R.id.tvTitle1)
        imgBack = root.findViewById(R.id.imgBack)
        loading = root.findViewById(R.id.loading)
        tvLyrics = root.findViewById(R.id.tvLyrics)
        Title = session!!.getData(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO_TITLE)
        Audio = session!!.getData(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO)
        Lyrics = session!!.getData(com.telugucalendar.telugupanchangamr.helper.Constant.LYRICS)
        Image = session!!.getData(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO_IMAGE)
        tvTitle!!.text = Title
        tvTitle1!!.text = Title
        tvLyrics!!.text = Lyrics
        tvLyrics!!.movementMethod = ScrollingMovementMethod()
        Glide.with(this).load(Image).placeholder(R.drawable.temple_img)
            .into(ivimage!!)
        sharedpreferences = requireActivity().getSharedPreferences("BBR", AppCompatActivity.MODE_PRIVATE)
        com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.hfmPlayer = MediaPlayer()
        curTime!!.text = "00:00"
        imgBack!!.setOnClickListener {

            if (hfmPlayer != null && hfmPlayer!!.isPlaying) {
                hfmPlayer!!.reset()
            }
            HomeActivity.fm!!.beginTransaction().replace(R.id.Container, AudioListFragment()).commit()

        }
        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
        playIcon!!.setOnClickListener { play() }
        forward!!.setOnClickListener { forwardSong() }
        backward!!.setOnClickListener { rewindSong() }
        imgLoop!!.setOnClickListener {
            loop = if (loop) {
                imgLoop!!.setImageResource(R.drawable.ic_baseline_loop_24)
                false
            } else {
                imgLoop!!.setImageResource(R.drawable.ic_baseline_loop_select)
                true
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {


                    if (hfmPlayer != null && hfmPlayer!!.isPlaying) {
                        hfmPlayer!!.reset()
                    }
                    HomeActivity.fm!!.beginTransaction().replace(R.id.Container, AudioListFragment()).commit()
                }
            })

        initPlayer()
        return root
    }

//    fun onBackPressed() {
//        Toast.makeText(requireActivity(), "1", Toast.LENGTH_SHORT).show()
//
////        super.requireActivity().onBackPressed()
//        if (hfmPlayer != null && hfmPlayer!!.isPlaying) {
//            hfmPlayer!!.reset()
//        }
//
//        HomeActivity.fm!!.beginTransaction().replace(R.id.Container, AudioListFragment()).commit()
//
//
//    }

    private fun initPlayer() {
        if (hfmPlayer == null) {
            hfmPlayer = MediaPlayer()
        } else {
            hfmPlayer!!.reset()
        }

        if (hfmPlayer == null) {
            // Handle the case where initialization failed
            return
        }

        hfmPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            hfmPlayer!!.setDataSource(Audio)
            hfmPlayer!!.prepareAsync()
            // below line is use to prepare
            // and start our media player.
        } catch (e: IOException) {
            e.printStackTrace()
        }

        hfmPlayer!!.setOnPreparedListener {
            val totalTime = createTimeLabel(hfmPlayer!!.duration)
            totTime!!.text = totalTime
            loading!!.visibility = View.GONE
            playIcon!!.visibility = View.VISIBLE
            mSeekBar!!.max = hfmPlayer!!.duration
            //playIcon.setBackgroundResource(R.drawable.b_play);
        }

        hfmPlayer!!.setOnCompletionListener {
            mSeekBar!!.progress = 0
            playIcon!!.setBackgroundResource(R.drawable.pause_new)
            curTime!!.text = "00:00"
            try {
                lyricsData = splandata?.getString("D1")
                lyricsText!!.text = lyricsData
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (count < setcount) {
                hfmPlayer!!.start()
                playIcon!!.setBackgroundResource(R.drawable.play_new)
                count++
            } else {
                //Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            if (loop) {
                play()
            }
        }

        mSeekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    hfmPlayer!!.seekTo(progress)
                    mSeekBar!!.progress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (!hfmPlayer!!.isPlaying) {
                    hfmPlayer!!.seekTo(0)
                    mSeekBar!!.progress = 0
                }
            }
        })

        Thread {
            while (hfmPlayer != null) {
                try {
                    if (hfmPlayer!!.isPlaying) {
                        val msg = Message()
                        msg.what = hfmPlayer!!.currentPosition
                        handler.sendMessage(msg)
                        Thread.sleep(1000)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val current_position = msg.what
            mSeekBar!!.progress = current_position
            val cTime = createTimeLabel(current_position)
            for (i in chalisatime.indices) {
                // Do something with chalisatime
            }
            curTime!!.text = cTime
        }
    }

    fun createTimeLabel(duration: Int): String {
        var timeLabel = ""
        val min = duration / 1000 / 60
        val sec = duration / 1000 % 60
        timeLabel += "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }

    private fun play() {
        if (hfmPlayer != null && !hfmPlayer!!.isPlaying) {
            playIcon!!.setBackgroundResource(R.drawable.play_new)
            hfmPlayer!!.start()
        } else {
            pause()
        }
    }

    private fun pause() {
        if (hfmPlayer!!.isPlaying) {
            playIcon!!.setBackgroundResource(R.drawable.pause_new)
            hfmPlayer!!.pause()
        }
    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            if (this == null) return
            // Implement your timer task logic
        }
    }

    fun forwardSong() {
        if (hfmPlayer != null) {
            val currentPosition = hfmPlayer!!.currentPosition
            if (currentPosition + seekForwardTime <= hfmPlayer!!.duration) {
                hfmPlayer!!.seekTo(currentPosition + seekForwardTime)
            } else {
                hfmPlayer!!.seekTo(hfmPlayer!!.duration)
            }
        }
    }

    fun rewindSong() {
        if (hfmPlayer != null) {
            val currentPosition = hfmPlayer!!.currentPosition
            if (currentPosition - seekBackwardTime >= 0) {
                hfmPlayer!!.seekTo(currentPosition - seekBackwardTime)
            } else {
                hfmPlayer!!.seekTo(0)
            }
        }
    }

    companion object {
        var hfmPlayer: MediaPlayer? = null
    }
}
