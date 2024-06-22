package com.example.crudmaxprocess.presentation.clientslist

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudmaxprocess.R
import com.example.crudmaxprocess.databinding.FragmentClientsListBinding
import com.example.crudmaxprocess.domain.model.Client
import com.example.crudmaxprocess.presentation.clientslist.adapter.ClientsListAdapter
import com.example.crudmaxprocess.presentation.utils.extensions.showClientDetailsBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel


class ClientsListFragment : Fragment(), ClientsListAdapter.ClickClient,ClientsListAdapter.ClickMoreOptions {
    private var _binding: FragmentClientsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClientsListViewModel by viewModel()

    private lateinit var clientsListAdapter: ClientsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClientsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getClients()
        observeEvents()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getClients()
        observeEvents()

    }

    private fun observeEvents() {
        viewModel.clientsList.observe(viewLifecycleOwner) { clients ->
            clientsListAdapter = ClientsListAdapter(clients, this,this)
            binding.rvClients.run {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = clientsListAdapter
                setHasFixedSize(true)
            }

        }

        viewModel.clientsListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ClientsListViewModel.ClientsListState.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvClients.visibility = View.GONE
                    binding.messageError.visibility = View.VISIBLE
                    binding.messageError.text = getText(R.string.show_client_error)
                }

                is ClientsListViewModel.ClientsListState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvClients.visibility = View.GONE
                    binding.messageError.visibility = View.GONE

                }

                is ClientsListViewModel.ClientsListState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvClients.visibility = View.VISIBLE
                    binding.messageError.visibility = View.GONE
                }

                ClientsListViewModel.ClientsListState.SuccessDelete -> {
                    Toast.makeText(
                        requireContext(),
                        "Cliente deletado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickMoreOptions(clients: Client, moreOptionsButton: ImageButton) {
        PopupMenu(requireContext(), moreOptionsButton).apply {
            menuInflater.inflate(R.menu.edit_menu, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit_item -> {
                        goToEditClients(clients)
                        true
                    }
                    R.id.delete_item -> {
                        showAddAlertDialog(clients)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }.show()
    }

    private fun showAddAlertDialog(clients: Client) {
        AlertDialog.Builder(requireContext())
            .setMessage(
                requireContext().getString(
                    R.string.delete_client_description,
                    clients.name
                )
            )
            .setTitle(getString(R.string.delete_clients))
            .setPositiveButton(getString(R.string.btn_delete),
                DialogInterface.OnClickListener { dialog, id ->
                    viewModel.deleteClient(clients)
                })
            .setNegativeButton(getString(R.string.btn_not),
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
            .create()
            .show()
    }

    private fun goToEditClients(clients: Client){
        val bundle = Bundle().apply {
            putParcelable("client", clients)
        }
        findNavController().navigate(R.id.saveClientsFragment, bundle)
    }
    override fun clickClient(clients: Client) {
        showClientDetailsBottomSheet(clients, { goToEditClients(clients) })
    }
}