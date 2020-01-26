package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result;
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.logging.Level
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A placeholder fragment containing a simple view.
 */
class LoginFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frag = inflater.inflate(R.layout.fragment_main, container, false)
        frag.login_btn.setOnClickListener {

            val username: String = frag.username.text.toString()
            val password: String = frag.password.text.toString()
            //retr
            // o fit

            // TODO: a changer avec nv url serveur cloud
            val retrofit = Retrofit.Builder()
                .baseUrl("http://localhost:3000")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val service = retrofit.create(CommunicationService::class.java)
            val request = service.getUserConfirmation(username, password)

            request.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val data = response.body()
                    if (data != null) {
                        Toast.makeText(activity, "a " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(activity, "KO" + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

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
        fun newInstance(sectionNumber: Int): LoginFragment {
            return LoginFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}