package com.example.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.AppActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.tour_layout.view.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * A placeholder fragment containing a simple view.
 */
class TourFragment : Fragment() {
    private lateinit var pageViewModel: PageViewModel

    private lateinit var playlist: List<Song>
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = activity?.intent?.extras?.get("username").toString()
        playlist = getSongs()
//        val songIds = getSongIds()
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    private fun getSongs(): List<Song> {
        val list: ArrayList<Song> = ArrayList()
        list.add(Song("Tous les mêmes", "Stromae", "1", "./images/stromae.jpg"))
        list.add(Song("Imperfections", "Céline Dion", "2", "./images/courage.png"))

        return list
    }

    private fun getSongIds(): List<SongId> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://concordia-hack.appspot.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(CommunicationService::class.java)
        val request = service.getTrackIdsFor(username, "20")
        val data: List<SongId> = ArrayList()
//        request.enqueue(object : Callback<List<SongId>> {
//            override fun onResponse(call: Call<List<SongId>>, response: Response<List<SongId>>) {
//                val data = response.body()
//                Toast.makeText(activity, "code:" + response.code(), Toast.LENGTH_SHORT).show()
//
//            }
//
//            override fun onFailure(call: Call<List<SongId>>, t: Throwable) {
//                Toast.makeText(activity, "KO" + t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
        return data
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frag = inflater.inflate(R.layout.tour_layout, container, false)

        return frag
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