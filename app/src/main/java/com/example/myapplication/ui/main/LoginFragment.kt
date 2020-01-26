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
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result;

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

//            val (request, response, result) = "localhost:3000/"
//                .httpGet()
//                .responseString()
//
//            when (result) {
//                is Result.Failure -> {
//                    val ex = result.getException()
//                    println(ex)
//                }
//                is Result.Success -> {
//                    val data = result.get()
//                    println(data)
//                }
//            }
        }
        /*val textView: TextView = root.findViewById(R.id.section_label)
        pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })*/
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