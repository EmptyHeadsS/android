package com.example.myapplication

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.ui.main.SectionsPagerAdapter
import android.content.Intent
import com.example.myapplication.ui.main.PlaceholderFragment

class PlayerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        //setupViews()
        setupListeners()
    }

/*    private fun setupViews () {
        SpotifyService.playingState {
            when(it) {
                PlayingState.PLAYING -> showPauseButton()
                PlayingState.STOPPED -> showPlayButton()
                PlayingState.PAUSED -> showResumeButton()
            }
        }
    }*/

    private fun setupListeners() {

        val playButton = findViewById<Button>(R.id.playButton)
        playButton.setOnClickListener {
            SpotifyService.play("spotify:album:5L8VJO457GXReKVVfRhzyM")
            //showPauseButton()
        }


        val pauseButton = findViewById<Button>(R.id.pauseButton)
        pauseButton.setOnClickListener {
            SpotifyService.pause()
            //showResumeButton()
        }

        val resumeButton = findViewById<Button>(R.id.resumeButton)
        resumeButton.setOnClickListener{
            SpotifyService.resume()
            //showPauseButton()
        }

        SpotifyService.suscribeToChanges {
            SpotifyService.getImage(it.imageUri){
                //trackImageView.setImageBitmap(it)
            }
        }
    }
}
