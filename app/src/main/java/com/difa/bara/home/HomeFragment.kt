package com.difa.bara.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.difa.bara.databinding.FragmentHomeBinding
import com.difa.bara.storage.DataStoreKeys
import com.difa.bara.storage.user
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var refMikrontroller: DatabaseReference
    private lateinit var refMikrontrollerESP8266: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        refMikrontroller = database.getReference("ESP32")
        refMikrontrollerESP8266 = database.getReference("ESP8266")

        showDataesp32()
        showDataesp8266()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        lifecycleScope.launch {
            getUserData(context)
        }

    }

    private suspend fun getUserData(context: Context) {
        context.user.data.collect { usrData ->
            val name = usrData[DataStoreKeys.NAME] ?: "none"

            requireActivity().runOnUiThread {
                binding.tvName.text = name
            }
        }
    }


    private fun showDataesp32() {
        refMikrontroller.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                refMikrontroller.get().addOnSuccessListener {
                    val time = it.child("Time").value.toString()
                    binding.tvTime.text = time
                    val carbon = it.child("Temp").value.toString()
                    binding.tvCarbon.text = "$carbon°C"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    private fun showDataesp8266() {
        refMikrontrollerESP8266.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                refMikrontrollerESP8266.get().addOnSuccessListener {
                    val roast = it.child("Temp").value.toString()
                    binding.tvRoasting.text = "$roast°C"
                    val fan = it.child("FAN").value.toString()
                    binding.tvFan.text = fan
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}