package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.repository.EntretienRepository

class EntretienViewModelFactory(private val repository: EntretienRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntretienViewModel::class.java)) {
            return EntretienViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
