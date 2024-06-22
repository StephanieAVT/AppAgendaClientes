package com.example.crudmaxprocess.domain.repository

import com.example.crudmaxprocess.data.database.entity.ClientEntity
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    suspend fun insert(clientEntity: ClientEntity): Long

    suspend fun updateClient(clientEntity: ClientEntity)

    suspend fun deleteClient(id: Long)

    fun getAllClients(): Flow<List<ClientEntity>>
}