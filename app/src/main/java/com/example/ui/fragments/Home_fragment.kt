package com.example.ui.fragments

import SwipeToDeleteCallback
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login_register_firebase.databinding.FragmentHomeFragmentBinding
import com.example.models.Client
import com.example.ui.adapters.ClientAdapter
import com.example.ui.viewmodels.ClientViewModel
import com.google.firebase.firestore.FirebaseFirestore

class Home_fragment : Fragment(), ClientAdapter.OnSwitchClickListener {

    private var _binding: FragmentHomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientAdapter: ClientAdapter
    private val listeDesClients = mutableListOf<Client>()
    private lateinit var clientViewModel: ClientViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)


        configurerRecyclerView()
        chargerClientsDepuisFirestore()

        val swipeHandler = SwipeToDeleteCallback(clientAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.listClients)


    }

    private fun configurerRecyclerView() {
        clientAdapter = ClientAdapter(listeDesClients, this)
        binding.listClients.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = clientAdapter
        }


    }

    private fun chargerClientsDepuisFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("clients")
            .get()
            .addOnSuccessListener { documents ->
                listeDesClients.clear()
                for (document in documents) {
                    val client = document.toObject(Client::class.java).apply {
                        id = document.id
                    }
                    listeDesClients.add(client)
                }
                clientAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("HomeFragment", "Erreur lors de la récupération des clients", exception)
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onSwitchClick(client: Client, clientName: String) {
        val fragment = FragmentCheckEntretien().apply {
            arguments = Bundle().apply {
                putString("client_name", clientName)
            }
        }
        fragment.show(parentFragmentManager, "checkEntretienDialog")
    }

    override fun onItemDismiss(clientId: String) {
        // Créer une boîte de dialogue de confirmation
        AlertDialog.Builder(context).apply {
            setTitle("Supprimer le client")
            setMessage("Êtes-vous sûr de vouloir supprimer ce client ?")
            setPositiveButton("Oui") { _, _ ->
                // Si l'utilisateur confirme, supprimez le client
                deleteClient(clientId)
            }
            setNegativeButton("Non") { dialog, _ ->
                // Si l'utilisateur annule, fermez la boîte de dialogue et rafraîchissez l'adapter
                dialog.dismiss()
                clientAdapter.notifyDataSetChanged()
            }
            create()
            show()
        }
    }

    private fun deleteClient(clientId: String) {
        // Trouvez l'index du client à supprimer
        val index = listeDesClients.indexOfFirst { it.id == clientId }
        if (index != -1) {
            // Supprimez le client de la liste
            listeDesClients.removeAt(index)
            // Notifiez l'adapter du changement
            clientAdapter.notifyItemRemoved(index)

            // Supprimez le client de la base de données
            clientViewModel.deleteClient(clientId,
                onSuccess = {
                    Toast.makeText(context, "Client supprimé avec succès", Toast.LENGTH_SHORT).show()
                },
                onFailure = { e ->
                    Toast.makeText(context, "Erreur de suppression : ${e.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }


}
