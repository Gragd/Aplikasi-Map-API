package com.example.aplikasimapkota

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

@Suppress("DEPRECATION")
class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private val cities = listOf("Jakarta", "Bogor", "Depok", "Tangerang", "Bekasi")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        val citySpinner = view.findViewById<Spinner>(R.id.citySpinner)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCity = cities[position]
                showCityOnMap(selectedCity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val geocoder = Geocoder(requireContext())

        for (city in cities) {
            try {
                val addresses = geocoder.getFromLocationName(city, 1)
                if (addresses?.isNotEmpty() == true) {
                    val address = addresses[0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    googleMap.addMarker(MarkerOptions().position(latLng).title(city))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (cities.isNotEmpty()) {
            val firstCity = cities[0]
            showCityOnMap(firstCity)
        }
    }

    private fun showCityOnMap(city: String) {
        val geocoder = Geocoder(requireContext())
        try {
            val addresses = geocoder.getFromLocationName(city, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val latLng = LatLng(address.latitude, address.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}