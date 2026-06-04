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
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val uid = result.user?.uid ?: return@addOnSuccessListener

                val user = User(
                    uid = uid,
                    email = email,
                    firstName = firstName,
                    lastName = lastName
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

    fun loginUser(
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

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser
}