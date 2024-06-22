package com.example.crudmaxprocess.presentation.clientslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.crudmaxprocess.R
import com.example.crudmaxprocess.domain.model.Client
import com.example.crudmaxprocess.presentation.utils.extensions.formatData


class ClientsListAdapter(
    private val clients: List<Client>,
    private var clickClient: ClickClient,
    private var clickMoreOptions: ClickMoreOptions
): RecyclerView.Adapter<ClientsListViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.client_list_item,parent,false)
        return ClientsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientsListViewHolder, position: Int) {
        holder.bindView(clients[position])
        holder.moreOptionsButton.setOnClickListener {
            clickMoreOptions.clickMoreOptions(clients[position], holder.moreOptionsButton)
        }
        holder.card.setOnClickListener{
            clickClient.clickClient(clients[position])
        }

    }

    override fun getItemCount() = clients.size

    interface ClickClient{
        fun clickClient(clients: Client)
    }
    interface ClickMoreOptions{
        fun clickMoreOptions(clients: Client, moreOptionsButton: ImageButton)
    }
}
class ClientsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val clientsName: TextView = itemView.findViewById(R.id.contact_name)
    private val clientsDate: TextView = itemView.findViewById(R.id.contact_date)
    val moreOptionsButton: ImageButton = itemView.findViewById(R.id.more_options_btn)
    val card: ConstraintLayout = itemView.findViewById(R.id.card_view)

    fun bindView(clients: Client){
        clientsName.text = clients.name
        clientsDate.text = "Created at: ${formatData(clients.createdAt)}"
    }
}
