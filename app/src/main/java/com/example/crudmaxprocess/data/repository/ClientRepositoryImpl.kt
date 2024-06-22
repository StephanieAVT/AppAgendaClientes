package com.example.crudmaxprocess.data.repository

import com.example.crudmaxprocess.data.database.dao.ClientDAO
import com.example.crudmaxprocess.data.database.entity.ClientEntity
import com.example.crudmaxprocess.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow


class ClientRepositoryImpl(
    private val clientDAO: ClientDAO
) : ClientRepository {
    override suspend fun insert(
        clientEntity: ClientEntity
    ): Long {
        return clientDAO.insert(clientEntity)
    }

    override suspend fun updateClient(
        clientEntity: ClientEntity
    ) {
        clientDAO.update(clientEntity)
    }

    override suspend fun deleteClient(id: Long) {
        clientDAO.delete(id)
    }

    override fun getAllClients(): Flow<List<ClientEntity>> {
        return clientDAO.getAllClients()
    }

}