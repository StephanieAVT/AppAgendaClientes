package com.example.crudmaxprocess.presentation.clientslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudmaxprocess.data.mapper.toDomain
import com.example.crudmaxprocess.domain.model.Client
import com.example.crudmaxprocess.domain.usecase.DeleteClientUseCase
import com.example.crudmaxprocess.domain.usecase.GetClientsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal class ClientsListViewModel(
    private val getClientsUseCase: GetClientsUseCase,
    private val deleteClientUseCase: DeleteClientUseCase
) :
    ViewModel() {

    private var _clientsList = MutableLiveData(mutableListOf<Client>())
    val clientsList: LiveData<MutableList<Client>> = _clientsList


    private val _clientsListState = MutableLiveData<ClientsListState>()
    val clientsListState: LiveData<ClientsListState>
        get() = _clientsListState

    init {
        getClients()
    }

    fun getClients() = viewModelScope.launch {
        getClientsUseCase()
            .onStart { _clientsListState.value = ClientsListState.Loading }
            .catch { _clientsListState.value = ClientsListState.Failure }
            .collect { clients ->
                _clientsList.value = clients.map { it.toDomain() }.toMutableList()
                _clientsListState.value = ClientsListState.Success

                if(clients.isNullOrEmpty())
                    _clientsListState.value = ClientsListState.Failure

            }
    }

    fun deleteClient(client: Client) = viewModelScope.launch {
        _clientsListState.value = ClientsListState.Loading
        try {
            deleteClientUseCase(client.id)
            _clientsListState.value = ClientsListState.SuccessDelete
            getClients()
        } catch (e: Exception) {
            _clientsListState.value = ClientsListState.Failure
        }
    }


    sealed class ClientsListState {
        object Loading : ClientsListState()
        object Success : ClientsListState()
        object Failure : ClientsListState()
        object SuccessDelete : ClientsListState()
    }

}
