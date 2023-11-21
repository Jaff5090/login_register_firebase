package com.example.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.login_register_firebase.databinding.FragmentAddClientBinding
import com.example.models.Client
import com.example.ui.viewmodels.ClientViewModel
import java.util.UUID


class profile_fragment : DialogFragment() {

        private var _binding: FragmentAddClientBinding? = null
        private val binding get() = _binding!!
        private lateinit var clientViewModel: ClientViewModel

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentAddClientBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
            binding.addClientButtom.setOnClickListener {
                addClient()
            }
        }

        private fun addClient() {
            val uniqueID = UUID.randomUUID().toString()

            val client = Client(
                id = uniqueID,
                nomDuClient = binding.NomduClient.text.toString(),
                codeClient = binding.CodeClient.text.toString(),
                email = binding.emailClient.text.toString(),
                numTelephone = binding.phoneClient.text.toString(),
                fax = binding.FaxClient.text.toString()
            )

            clientViewModel.addClient(client,
                onSuccess = {
                    Toast.makeText(context, "Client ajouté avec succès", Toast.LENGTH_SHORT).show()
                },
                onFailure = { e ->
                    Toast.makeText(context, "Erreur: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
        override fun onStart() {
            super.onStart()
            dialog?.window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(null)  // Pour un arrière-plan transparent ou autre, si nécessaire
            }
        }
    }