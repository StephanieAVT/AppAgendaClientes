package com.example.crudmaxprocess.domain.usecase

import com.example.crudmaxprocess.data.mapper.toEntity
import com.example.crudmaxprocess.domain.model.Client
import com.example.crudmaxprocess.domain.repository.ClientRepository

internal class InsertClientUseCase(private val clientRepository: ClientRepository) {
    suspend operator fun invoke(client: Client):Long = clientRepository.insert(client.toEntity())
}