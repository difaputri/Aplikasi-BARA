package com.difa.bara.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.edit
import com.difa.bara.about.AboutActivity
import com.difa.bara.account.AccountActivity
import com.difa.bara.databinding.FragmentSettingsBinding
import com.difa.bara.login.LoginActivity
import com.difa.bara.storage.user
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvLogout.setOnClickListener{
            Firebase.auth.signOut()
            CoroutineScope(Dispatchers.IO).launch {
                deleteUserData(requireContext())
            }
        }

        binding.cvAkun.setOnClickListener {
            startActivity(Intent(requireContext(), AccountActivity::class.java))
        }

        binding.cvAbout.setOnClickListener {
            startActivity(Intent(requireContext(), AboutActivity::class.java))
        }
    }

    private suspend fun deleteUserData(context: Context) {
        context.user.edit { data ->
           data.clear()
        }
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
        parentFragmentManager.popBackStack()
    }


}