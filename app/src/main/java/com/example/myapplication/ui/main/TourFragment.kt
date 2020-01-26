package com.example.myapplication.ui.main

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import com.google.android.gms.location.LocationServices;



/**
 * A placeholder fragment containing a simple view.
 */
class TourFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        getLocation()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frag = inflater.inflate(R.layout.tour_layout, container, false)

        return frag
    }

    fun getLocation() {
        var lon: Double = 45.523531
        var lat: Double = -73.594541
/*
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this.activity!!, permissions,0)*/


        /*fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!.applicationContext!!)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                lon = location?.longitude ?: 45.523531
                lat = location?.latitude ?: -73.594541
                Log.println(Log.DEBUG, "yes", lon.toString())
                val geocoder = Geocoder(this.context, Locale.getDefault())
                val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
                var formattedlocation = formatLocation(addresses)
                Log.println(Log.DEBUG, "yes", formattedlocation.city)
                requestPlaylist(formattedlocation)
            }*/
        val geocoder = Geocoder(this.context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
        var formattedlocation = formatLocation(addresses)
        Log.println(Log.DEBUG, "yes", formattedlocation.city)
        requestPlaylist(formattedlocation)
    }

    fun formatLocation(addresses: List<Address>) : CityLocation {
        val address = addresses[0].getAddressLine(0).split(",").toTypedArray()
        Log.println(Log.DEBUG,"yes", address[0])
        var cityName = address[1]
        var countryName = address.last()
        var location = CityLocation()
        location.city = cityName
        location.country = countryName
        return location
    }

    fun requestPlaylist(location: CityLocation) {
        val thread = Thread(Runnable {
            try {
                var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode("alexandre", "UTF-8")
                reqParam += "&" + URLEncoder.encode("location", "UTF-8") + ("={\"city\": \"" + location.city + "\", \"country\": \"" + location.country + "\"}")

                val mURL = URL("https://concordia-hack.appspot.com/updateLocation?"+reqParam)

                with(mURL.openConnection() as HttpURLConnection) {
                    requestMethod = "POST"

                    println("URL : $url")
                    println("Response Code : $responseCode")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        it.close()
                        println("Response : $response")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start()


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

class CityLocation {
    var city: String = ""
    var country: String = ""
}