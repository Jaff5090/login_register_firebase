package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.models.Client
import com.google.firebase.firestore.FirebaseFirestore

class ClientViewModel : ViewModel() {

    fun addClient(client: Client, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("clients").add(client)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun deleteClient(clientId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("clients").document(clientId).delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }


}