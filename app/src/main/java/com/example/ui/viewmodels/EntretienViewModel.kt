package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.models.Entretien
import com.example.repository.EntretienRepository

class EntretienViewModel(private val repository: EntretienRepository) : ViewModel() {

    fun sauvegarderEntretien(entretien: Entretien, onSuccess: () -> Unit) {
        repository.enregistrerEntretien(entretien,
            onSuccess = {
                onSuccess()
            },
            onFailure = { exception ->
            }
        )
    }
}
