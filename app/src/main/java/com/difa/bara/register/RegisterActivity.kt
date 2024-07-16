package com.difa.bara.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.difa.bara.R
import com.difa.bara.databinding.ActivityLoginBinding
import com.difa.bara.databinding.ActivityRegisterBinding
import com.difa.bara.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btRegister.setOnClickListener{
            register()
        }
        binding.btLoginSignUp.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    private fun register() {
        val name = binding.edNameSignUp.text.toString()
        val email = binding.edEmailSignUp.text.toString()
        val password = binding.edPasswordSignUp.text.toString()


        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileTask ->
                                if (profileTask.isSuccessful) {
                                    Toast.makeText(this, "User created successfully.", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Fields are required.", Toast.LENGTH_SHORT).show()
        }
    }
}