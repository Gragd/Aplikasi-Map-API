package com.example.aplikasimapkota.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasimapkota.R
import com.example.aplikasimapkota.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var currentBackground = 1
    private val backgrounds = arrayOf(
        R.drawable.background1,
        R.drawable.background2,
        R.drawable.background3,
        R.drawable.background4
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val backgroundResId = sharedPreferences.getInt("backgroundId", R.drawable.background1)


        binding.root.setBackgroundResource(backgroundResId)

        binding.btnUbahBg.setOnClickListener {
            changeBackground()
        }
    }

    private fun changeBackground() {
        currentBackground = (currentBackground % backgrounds.size) + 1
        val backgroundResId = backgrounds[currentBackground - 1]
        binding.root.setBackgroundResource(backgroundResId)

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("backgroundId", backgroundResId)
        editor.apply()
    }

}