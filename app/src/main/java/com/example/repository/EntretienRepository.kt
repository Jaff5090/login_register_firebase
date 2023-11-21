package com.example.repository

import com.example.models.Entretien
import com.google.firebase.firestore.FirebaseFirestore

class EntretienRepository {

    private val db = FirebaseFirestore.getInstance()
    fun enregistrerEntretien(entretien: Entretien, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("entretiens")
            .add(entretien)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }
}
