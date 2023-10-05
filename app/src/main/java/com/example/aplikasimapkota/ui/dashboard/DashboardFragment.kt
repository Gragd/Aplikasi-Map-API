package com.example.aplikasimapkota.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.aplikasimapkota.R
import com.example.aplikasimapkota.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!


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
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val editTextName: EditText = binding.editTextName
        val editTextHobby: EditText = binding.editTextHobby
        val buttonSave: Button = binding.buttonSave

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val hobby = editTextHobby.text.toString()

            val sharedPreferences =
                requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.putString("hobby", hobby)
            editor.apply()

            editTextName.text.clear()
            editTextHobby.text.clear()

            updateToolbarName(name)

        }

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val backgroundResId = sharedPreferences.getInt("backgroundId", R.drawable.background1)

        binding.root.setBackgroundResource(backgroundResId)

        return root
    }

    private fun updateToolbarName(name: String) {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        val textViewName = toolbar.findViewById<TextView>(R.id.textViewName)
        textViewName.text = name
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}