package com.example.myapplication.ui.main

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.nio.file.Paths
import kotlinx.android.synthetic.main.tour_layout.*
import java.util.*


/**
 * A placeholder fragment containing a simple view.
 */
class TourFragment : Fragment() {
    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0
    private lateinit var pageViewModel: PageViewModel
    private lateinit var frag: View

    private lateinit var playlist: LinkedList<Pair<Int, SongMeta>>
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = activity?.intent?.extras?.get("username").toString()
//        playlist = getSongs()
//        val songIds = getSongIds()
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }

    }
    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what

            // Update positionBar
            positionBar.progress = currentPosition

            // Update Labels
            var elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLabel.text = elapsedTime

            var remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingTimeLabel.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        frag = inflater.inflate(R.layout.tour_layout, container, false)
        val positionBar = frag.findViewById<SeekBar>(R.id.positionBar)
        val volumeBar = frag.findViewById<SeekBar>(R.id.volumeBar)
        val playBouton = frag.findViewById<Button>(R.id.playBtn)
        playlist = LinkedList()
        if (username.equals("Alexandre")) {
            //hip hop
            playlist.add(Pair(R.raw.hm1, SongMeta("10%", "KAYTRANADA", R.drawable.kaytranada)))
            playlist.add(Pair(R.raw.hm2, SongMeta("L'artiste", "Chocolat", R.drawable.chocolat)))
            playlist.add(Pair(R.raw.hm3, SongMeta("Lemonade", "Da-P", R.drawable.dap)))
        } else if (username.equals("Esther")) {
            // Indie
            playlist.add(Pair(R.raw.im1, SongMeta("Trailwhip", "Men I Trust", R.drawable.menitrust)))
            playlist.add(Pair(R.raw.im2, SongMeta("Bricolage", "Geneviève Racette", R.drawable.racette)))
            playlist.add(Pair(R.raw.im3, SongMeta("Fil de soi", "Marie-Pierre Arthur", R.drawable.mariepierrearthur)))
        }


        playBouton.setOnClickListener {
            if (mp.isPlaying) {
                // Stop
                mp.pause()
                playBtn.setBackgroundResource(R.drawable.play)

            } else {
                // Start
                mp.start()
                playBtn.setBackgroundResource(R.drawable.stop)
            }
        }

            if (volumeBar != null) {
                mp = MediaPlayer.create(activity, playlist.pop().first)
                //mp.isLooping = true
                mp.setVolume(0.5f, 0.5f)
                totalTime = mp.duration



                // Volume Bar

                volumeBar.setOnSeekBarChangeListener(
                    object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekbar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            if (fromUser) {
                                var volumeNum = progress / 100.0f
                                mp.setVolume(volumeNum, volumeNum)
                            }
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(p0: SeekBar?) {
                        }
                    }
                )

                // Position Bar
                positionBar.max = totalTime
                positionBar.setOnSeekBarChangeListener(
                    object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            if (fromUser) {
                                mp.seekTo(progress)
                            }
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(p0: SeekBar?) {
                        }
                    }
                )

                mp.setOnCompletionListener{
                    initialiseMediaPlayer()
                    mp.start()
                }


                // Thread
                Thread(Runnable {
                    while (mp != null) {
                        try {
                            var msg = Message()
                            msg.what = mp.currentPosition
                            handler.sendMessage(msg)
                            Thread.sleep(1000)

                        } catch (e: InterruptedException) {
                        }
                    }
                }).start()
            }


//        displaySong(playlist[0])

        return frag
    }
    private fun initialiseMediaPlayer() {

        if (volumeBar != null && !playlist.isEmpty()) {
            val track = playlist.pop()

            //update visual
            cover.setBackgroundResource(track.second.imagePath)
            artist.setText(track.second.artist)
            songName.setText(track.second.songTitle)

            mp = MediaPlayer.create(activity,track.first)
            //mp.isLooping = true
            mp.setVolume(0.5f, 0.5f)
            totalTime = mp.duration

            // Volume Bar

            volumeBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekbar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        if (fromUser) {
                            var volumeNum = progress / 100.0f
                            mp.setVolume(volumeNum, volumeNum)
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }
                }
            )

            // Position Bar
            positionBar.max = totalTime
            positionBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        if (fromUser) {
                            mp.seekTo(progress)
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }
                }
            )
        }



    }
    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): TourFragment {
            return TourFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}