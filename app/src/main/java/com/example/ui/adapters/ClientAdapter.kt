package com.example.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login_register_firebase.R
import com.example.models.Client
import com.google.android.material.switchmaterial.SwitchMaterial


class ClientAdapter(
    val clients: List<Client>,
    val listener: OnSwitchClickListener
) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.itemviewclient, parent, false)
        return ClientViewHolder(view, listener)
    }

    fun updateClientStatus(clientName: String, statusColor: Int) {
        val index = clients.indexOfFirst { it.nomDuClient == clientName }
        if (index != -1) {

            notifyItemChanged(index)
        }
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        holder.bind(client)
        Log.d("ClientAdapter", "Binding client: ${client.nomDuClient}")

    }

    override fun getItemCount(): Int = clients.size

    class ClientViewHolder(view: View, private val listener: OnSwitchClickListener) :
        RecyclerView.ViewHolder(view) {
        private val tvNomDuClient: TextView = view.findViewById(R.id.tvNomDuClient)
        private val codeClient: TextView = view.findViewById(R.id.codeClient)
        private val email: TextView = view.findViewById(R.id.emailClient)
        private val telephone: TextView = view.findViewById(R.id.Telephone)
        private val fax: TextView = view.findViewById(R.id.Fax)
        private val switchEntretien: SwitchMaterial = view.findViewById(R.id.switch_entretien)


        fun bind(client: Client) {
            tvNomDuClient.text = client.nomDuClient
            codeClient.text = client.codeClient
            email.text = client.email
            telephone.text = "Tél: ${client.numTelephone}"
            fax.text = "Fax: ${client.fax}"



            switchEntretien.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    listener.onSwitchClick(client, client.nomDuClient)
                }
            }
        }

    }

    interface OnSwitchClickListener {
        fun onSwitchClick(client: Client, clientName: String)
        fun onItemDismiss(clientId: String)
    }


}
