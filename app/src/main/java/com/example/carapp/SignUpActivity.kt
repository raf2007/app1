package com.example.carapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val buttonSignUp: Button = findViewById(R.id.buttonSignUp)
        buttonSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val username = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()

        Log.d("SignUpActivity", "Sign up initiated with username: $username, email: $email")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateProfile(user, username)
                    // You can add additional actions here, such as navigating to another screen.
                } else {
                    // If sign up fails, display a message to the user.
                    // You might want to handle different failure scenarios.
                    val exception = task.exception
                    Log.e("SignUpActivity", "Sign up failed: ${exception?.message}", exception)
                }
            }
    }

    private fun updateProfile(user: FirebaseUser?, username: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Profile updated successfully
                    Log.d("SignUpActivity", "Profile updated successfully for user: ${user.displayName}")
                } else {
                    // Handle the error
                    val exception = task.exception
                    Log.e("SignUpActivity", "Profile update failed: ${exception?.message}", exception)
                }
            }
    }
}
