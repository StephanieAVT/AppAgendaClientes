package com.example.crudmaxprocess.presentation.clientslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.crudmaxprocess.data.mapper.toEntity
import com.example.crudmaxprocess.domain.model.Client
import com.example.crudmaxprocess.domain.usecase.DeleteClientUseCase
import com.example.crudmaxprocess.domain.usecase.GetClientsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ClientsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getClientsUseCase: GetClientsUseCase

    @Mock
    private lateinit var deleteClientUseCase: DeleteClientUseCase

    @Mock
    private lateinit var observer: Observer<ClientsListViewModel.ClientsListState>

    private lateinit var viewModel: ClientsListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = ClientsListViewModel(getClientsUseCase, deleteClientUseCase)
    }

    @Test
    fun `getClients returns Success when clients list is not empty`() = runTest {
        val clients = listOf(
            Client(
                id = 1,
                name = "Test Client",
                birthDate = "1990-01-01",
                cpf = "123.456.789-00",
                createdAt = "2022-01-01",
                phone = "123456789",
                phone2 = "987654321",
                uf = "SP"
            )
        )
        `when`(getClientsUseCase()).thenReturn(flowOf(clients.map { it.toEntity() }))

        viewModel.clientsListState.observeForever(observer)
        viewModel.getClients()

        verify(observer).onChanged(ClientsListViewModel.ClientsListState.Loading)
        verify(observer).onChanged(ClientsListViewModel.ClientsListState.Success)
    }

    @Test
    fun `getClients returns Failure when clients list is empty`() = runTest {
        `when`(getClientsUseCase()).thenReturn(flowOf(listOf()))

        viewModel.clientsListState.observeForever(observer)
        viewModel.getClients()

        verify(observer).onChanged(ClientsListViewModel.ClientsListState.Loading)
        verify(observer).onChanged(ClientsListViewModel.ClientsListState.Failure)
    }

    @Test
    fun `deleteClient returns SuccessDelete when client deletion is successful`() = runTest {
        val client = Client(
            id = 1,
            name = "Test Client",
            birthDate = "1990-01-01",
            cpf = "123.456.789-00",
            createdAt = "2022-01-01",
            phone = "123456789",
            phone2 = "987654321",
            uf = "SP"
        )
        `when`(deleteClientUseCase(client.id)).thenReturn(Unit)

        viewModel.clientsListState.observeForever(observer)
        viewModel.deleteClient(client)

        verify(observer).onChanged(ClientsListViewModel.ClientsListState.Loading)
        verify(observer).onChanged(ClientsListViewModel.ClientsListState.SuccessDelete)
    }

    @Test
    fun `deleteClient returns Failure when client deletion fails`() = runTest {
        val client = Client(
            id = 1,
            name = "Test Client",
            birthDate = "1990-01-01",
            cpf = "123.456.789-00",
            createdAt = "2022-01-01",
            phone = "123456789",
            phone2 = "987654321",
            uf = "SP"
        )
        `when`(deleteClientUseCase(client.id)).thenThrow(RuntimeException())

        viewModel.clientsListState.observeForever(observer)
        viewModel.deleteClient(client)

        verify(observer).onChanged(ClientsListViewModel.ClientsListState.Loading)
        verify(observer).onChanged(ClientsListViewModel.ClientsListState.Failure)
    }
}