package com.example.collectorscove.data.repository

import com.example.collectorscove.data.firebase.FirebaseManager
import com.example.collectorscove.data.model.User

class AuthRepository {

    private val auth = FirebaseManager.auth
    private val firestore = FirebaseManager.firestore

    fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String = "",
        address: String = "",
        nationality: String = "",
        gender: String = "",
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
            .addOnFailureListener { e ->
                val errorMessage = if (e.message?.contains("already in use") == true) {
                    "This email is already registered in Firebase Authentication. Please delete it from the 'Authentication' tab in the Firebase Console if you want to start fresh."
                } else {
                    e.message
                }
                onResult(false, errorMessage)
            }
    }

    fun loginUser(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                val errorMessage = if (e.message?.contains("already in use") == true) {
                    "This email is already registered in Firebase Authentication. Please delete it from the 'Authentication' tab in the Firebase Console if you want to start fresh."
                } else {
                    e.message
                }
                onResult(false, errorMessage)
            }
    }

    fun getUserProfile(uid: String, onResult: (User?) -> Unit) {
        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                onResult(user)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser
}