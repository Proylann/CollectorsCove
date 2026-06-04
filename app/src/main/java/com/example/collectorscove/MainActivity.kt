package com.example.collectorscove

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.collectorscove.ui.theme.CollectorsCoveTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Firebase test
        val db = FirebaseFirestore.getInstance()

        val testData = hashMapOf(
            "message" to "Firebase Connected"
        )

        db.collection("test")
            .add(testData)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Connected Successfully")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error: ${it.message}")
            }

        setContent {
            CollectorsCoveTheme {
                AppNavigation()
            }
        }
    }
}
