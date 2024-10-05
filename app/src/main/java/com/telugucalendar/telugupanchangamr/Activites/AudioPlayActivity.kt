package com.telugucalendar.telugupanchangamr.Activites

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.telugupanchangam.telugucalender.R
import org.json.JSONObject
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

class AudioPlayActivity : AppCompatActivity() {
    var bellPlayer: MediaPlayer? = null
    var shankPlayer: MediaPlayer? = null
    private var playIcon: ImageButton? = null
    private var forward: ImageButton? = null
    private var backward: ImageButton? = null
    private val shankhBtn: ImageButton? = null
    var imgLoop: ImageView? = null
    private var mSeekBar: SeekBar? = null
    private val viewPager2: ViewPager2? = null
    private var totTime: TextView? = null
    private var curTime: TextView? = null
    private var tvTitle: TextView? = null
    private var tvTitle1: TextView? = null
    private var count = 1
    private val setcount = 0
    private val seekForwardTime = 10 * 1000 // default 5 second
    private val seekBackwardTime = 10 * 1000 // default 5 second
    var Title: String? = null
    private val timer: String? = null
    private val lyricsText: TextView? = null
    private val isPlaying = false
    var jsonload = false
    private val obj: JSONObject? = null
    private val splandata: JSONObject? = null
    private var lyricsData: String? = null
    private var Audio: String? = null
    private var Lyrics: String? = null
    private var Image: String? = null
    var mediaPlayer: MediaPlayer? = null
    var imgBack: ImageView? = null
    var sharedpreferences: SharedPreferences? = null
    var loading: CircularProgressIndicator? = null
    var ivimage: ImageView? = null
    var tvLyrics: TextView? = null
    var loop = false
    var chalisatime = arrayOf(
        "0:00",
        "0:23",
        "0:55",
        "1:17",
        "1:44",
        "2:06",
        "2:30",
        "2:53",
        "3:18",
        "3:40",
        "4:06",
        "4:28",
        "4:54",
        "5:16",
        "5:42",
        "6:04",
        "6:29",
        "6:52",
        "7:17",
        "7:39",
        "8:04",
        "8:26",
        "9:00"
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_play)
        curTime = findViewById(R.id.currentState)
        ivimage = findViewById(R.id.ivimage)
        totTime = findViewById(R.id.totalValue)
        mSeekBar = findViewById(R.id.mySeekbar)
        playIcon = findViewById(R.id.playBtn)
        imgLoop = findViewById(R.id.imgLoop)
        forward = findViewById(R.id.forward)
        backward = findViewById(R.id.backward)
        tvTitle = findViewById(R.id.tvTitle)
        tvTitle1 = findViewById(R.id.tvTitle1)
        imgBack = findViewById(R.id.imgBack)
        loading = findViewById(R.id.loading)
        tvLyrics = findViewById(R.id.tvLyrics)
        Title = intent.getStringExtra(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO_TITLE)
        Audio = intent.getStringExtra(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO)
        Lyrics = intent.getStringExtra(com.telugucalendar.telugupanchangamr.helper.Constant.LYRICS)
        Image = intent.getStringExtra(com.telugucalendar.telugupanchangamr.helper.Constant.AUDIO_IMAGE)
        tvTitle!!.setText(Title)
        tvTitle1!!.setText(Title)
        tvLyrics!!.setText(Lyrics)
        tvLyrics!!.setMovementMethod(ScrollingMovementMethod())
        Glide.with(this@AudioPlayActivity).load(Image).placeholder(R.drawable.temple_img)
            .into(ivimage!!)
        sharedpreferences = getSharedPreferences("BBR", MODE_PRIVATE)
        com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer = MediaPlayer()
        bellPlayer = MediaPlayer()
        shankPlayer = MediaPlayer()
        initPlayer()
        curTime!!.setText("00:00")
        imgBack!!.setOnClickListener(View.OnClickListener { onBackPressed() })
        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
        playIcon!!.setOnClickListener(View.OnClickListener { play() })
        forward!!.setOnClickListener(View.OnClickListener { forwardSong() })
        backward!!.setOnClickListener(View.OnClickListener { rewindSong() })
        imgLoop!!.setOnClickListener(View.OnClickListener {
            loop = if (loop) {
                imgLoop!!.setImageResource(R.drawable.ic_baseline_loop_24)
                false
            } else {
                imgLoop!!.setImageResource(R.drawable.ic_baseline_loop_select)
                true
            }
        })


        //playAudio();
    }

    private fun playAudio() {
        val audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"

        // initializing media player
        mediaPlayer = MediaPlayer()

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer!!.setDataSource(audioUrl)
            mediaPlayer!!.prepareAsync()
            // below line is use to prepare
            // and start our media player.
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaPlayer!!.setOnPreparedListener { player -> player.start() }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer != null && com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.isPlaying) {
            com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.reset()
        }
    }

    private fun initPlayer() {
        if (com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer != null && com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.isPlaying) {
            com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.reset()
        }
        com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.setDataSource(Audio)
            com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.prepareAsync()
            // below line is use to prepare
            // and start our media player.
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //hfmPlayer = MediaPlayer.create(AudioPlayActivity.this,R.raw.chalisa); // create and load mediaplayer with song resources
        com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.setOnPreparedListener {
            val totalTime = createTimeLabel(com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.duration)
            totTime!!.text = totalTime
            loading!!.visibility = View.GONE
            playIcon!!.visibility = View.VISIBLE
            mSeekBar!!.max = com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.duration
            //playIcon.setBackgroundResource(R.drawable.b_play);
        }
        com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.setOnCompletionListener {
            mSeekBar!!.progress = 0
            playIcon!!.setBackgroundResource(R.drawable.b_play)
            curTime!!.text = "00:00"
            try {
                lyricsData = splandata!!.getString("D1")
                lyricsText!!.text = lyricsData
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (count < setcount) {
                com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.start()
                playIcon!!.setBackgroundResource(R.drawable.b_pause)
                count++
            } else {
                //Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            if (loop) {
                play()
            }
        }
        mSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.seekTo(progress)
                    mSeekBar!!.progress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (!com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.isPlaying) {
                    com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.seekTo(0)
                    mSeekBar!!.progress = 0
                }
            }
        })
        Thread {
            while (com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer != null) {
                try {
//                        Log.i("Thread ", "Thread Called");
                    // create new message to send to handler
                    if (com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.isPlaying) {
                        val msg = Message()
                        msg.what = com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.currentPosition
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
//            Log.i("handler ", "handler called");
            val current_position = msg.what
            mSeekBar!!.progress = current_position
            val cTime = createTimeLabel(current_position)
            for (i in chalisatime.indices) {
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
        if (com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer != null && !com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.isPlaying) {
            playIcon!!.setBackgroundResource(R.drawable.b_pause)
            com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.start()
        } else {
            pause()
        }
    }

    private fun pause() {
        if (com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.isPlaying) {
            playIcon!!.setBackgroundResource(R.drawable.b_play)
            com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.pause()
        }
    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            if (this@AudioPlayActivity == null) return
        }
    }

    fun forwardSong() {
        if (com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer != null) {
            val currentPosition = com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.currentPosition
            if (currentPosition + seekForwardTime <= com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.duration) {
                com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.seekTo(currentPosition + seekForwardTime)
            } else {
                com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.seekTo(
                    com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.duration)
            }
        }
    }

    fun rewindSong() {
        if (com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer != null) {
            val currentPosition = com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.currentPosition
            if (currentPosition - seekBackwardTime >= 0) {
                com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.seekTo(currentPosition - seekBackwardTime)
            } else {
                com.telugucalendar.telugupanchangamr.Activites.AudioPlayActivity.Companion.hfmPlayer!!.seekTo(0)
            }
        }
    }

    companion object {
        var hfmPlayer: MediaPlayer? = null
    }
}