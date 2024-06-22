package com.example.crudmaxprocess.presentation.saveclients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.crudmaxprocess.R
import com.example.crudmaxprocess.databinding.FragmentSaveClientsBinding
import com.example.crudmaxprocess.domain.model.Client
import com.example.crudmaxprocess.presentation.utils.extensions.hideKeyboard
import com.example.crudmaxprocess.presentation.utils.state.StateView
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar


class SaveClientsFragment : Fragment() {


    private var _binding: FragmentSaveClientsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SaveClientsViewModel by viewModel()

    private var client: Client? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveClientsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configSpinner()
        observeEvents()
        configClickListeners()
        getArgs()
    }

    private fun getArgs() {
        arguments?.let {
            client = it.getParcelable("client")
        }
        if (client != null) {
            configClient(client)
        }else {
            configScreen()
        }

    }

    private fun configClient(client: Client?) {
        binding.cardView.text = getString(R.string.edit_clients_description)
        binding.btnEditClient.isVisible = true
        binding.btnSaveClient.isVisible = false
        binding.btnBack.isVisible = true

        if (client == null) return
        else{
            binding.clientName.setText(client.name)
            binding.clientCPF.setText(client.cpf)
            binding.phone.setText(client.phone)
            binding.clientBirthDate.setText(client.birthDate)
            binding.phone2.setText(client.phone2)
            binding.UF.setSelection(resources.getStringArray(R.array.uf_array).indexOf(client.uf))
        }

    }

    private fun configScreen() {
        binding.cardView.text = getString(R.string.save_clients_description)
        binding.btnEditClient.isVisible = false
        binding.btnSaveClient.isVisible = true
    }

    private fun configSpinner() {
        val spinner: Spinner = binding.UF
        ArrayAdapter.createFromResource(
            requireContext().applicationContext,
            R.array.uf_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun saveClients(client: Client) {
        viewModel.saveClient(client).observe(viewLifecycleOwner) { state ->
            when (state) {
                is StateView.Loading -> {
                    Toast.makeText(requireContext(), "Salvando cliente...", Toast.LENGTH_SHORT)
                        .show()
                }

                is StateView.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Cliente salvo com sucesso com id: ${state.data}",
                        Toast.LENGTH_SHORT
                    ).show()
                    clearFields()
                    hideKeyboard()

                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            }
        }
    }

    private fun updateClient(client: Client) {
        viewModel.updateClient(client).observe(viewLifecycleOwner) { state ->
            when (state) {
                is StateView.Loading -> {
                    Toast.makeText(requireContext(), "Editando cliente...", Toast.LENGTH_SHORT)
                        .show()
                }

                is StateView.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Dados do cliente ${client.name} atualizados com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    clearFields()
                    hideKeyboard()
                    findNavController().navigate(R.id.homeFragment)

                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            }
        }
    }

    private fun observeEvents() {
        viewModel.messageEventData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun configClickListeners() {
        binding.btnSaveClient.setOnClickListener {
            val client = Client(
                name = binding.clientName.text.toString(),
                cpf = binding.clientCPF.text.toString(),
                createdAt = Calendar.getInstance().time.toString(),
                phone = binding.phone.text.toString(),
                birthDate = binding.clientBirthDate.text.toString(),
                phone2 = binding.phone2.text.toString(),
                uf = binding.UF.selectedItem.toString()
            )
            saveClients(client)
        }
        binding.btnEditClient.setOnClickListener {
            val client = Client(
                name = binding.clientName.text.toString(),
                cpf = binding.clientCPF.text.toString(),
                createdAt = Calendar.getInstance().time.toString(),
                phone = binding.phone.text.toString(),
                birthDate = binding.clientBirthDate.text.toString(),
                phone2 = binding.phone2.text.toString(),
                uf = binding.UF.selectedItem.toString()
            )
            updateClient(client)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun clearFields() {
        with(binding) {
            clientName.text?.clear()
            clientCPF.text?.clear()
            phone.text?.clear()
            clientBirthDate.text?.clear()
            phone2.text?.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
