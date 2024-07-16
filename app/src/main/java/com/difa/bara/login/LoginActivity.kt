package com.difa.bara.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.difa.bara.R
import com.difa.bara.databinding.ActivityLoginBinding
import com.difa.bara.home.MainActivity
import com.difa.bara.register.RegisterActivity
import com.difa.bara.storage.DataStoreKeys
import com.difa.bara.storage.user
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btSignup.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btLogin.setOnClickListener{
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.getIdToken(true)
                            ?.addOnCompleteListener { tokenTask ->
                                if (tokenTask.isSuccessful) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        saveUserData(
                                            user.displayName.toString(),
                                            user.email.toString(),
                                            tokenTask.result?.token.toString()
                                        )
                                    }
                                } else {
                                    Toast.makeText(baseContext, "Invalid credentials, try again.",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(baseContext, "Invalid credentials, try again.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
    private suspend fun saveUserData(name: String, email: String, token: String) {
        user.edit { data ->
            data[DataStoreKeys.NAME] = name
            data[DataStoreKeys.EMAIL] = email
            data[DataStoreKeys.TOKEN] = token
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}