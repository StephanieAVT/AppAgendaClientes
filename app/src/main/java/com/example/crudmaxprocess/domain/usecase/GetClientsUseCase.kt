package com.example.crudmaxprocess.domain.usecase

import com.example.crudmaxprocess.data.database.entity.ClientEntity
import com.example.crudmaxprocess.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow

internal class GetClientsUseCase(
    private val clientRepository: ClientRepository
){
     operator fun invoke(): Flow<List<ClientEntity>> = clientRepository.getAllClients()
}