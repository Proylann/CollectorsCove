package com.example.collectorscove.ui.auth

import androidx.lifecycle.ViewModel
import com.example.collectorscove.data.firebase.FirebaseManager
import com.example.collectorscove.data.model.User

class AuthViewModel : ViewModel() {

    private val auth = FirebaseManager.auth
    private val firestore = FirebaseManager.firestore

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        address: String,
        nationality: String,
        gender: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val uid = result.user?.uid ?: return@addOnSuccessListener

                val user = User(
                    uid = uid,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    address = address,
                    nationality = nationality,
                    gender = gender
                )

                firestore.collection("users")
                    .document(uid)
                    .set(user)
                    .addOnSuccessListener {
                        onResult(true, null)
                    }
                    .addOnFailureListener {
                        onResult(false, it.message)
                    }
            }
            .addOnFailureListener {
                onResult(false, it.message)
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener {
                onResult(false, it.message)
            }
    }
}