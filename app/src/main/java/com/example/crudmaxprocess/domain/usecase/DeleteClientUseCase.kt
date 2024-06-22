package com.example.crudmaxprocess.domain.usecase

import com.example.crudmaxprocess.domain.repository.ClientRepository

internal class DeleteClientUseCase(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(id:Long) = clientRepository.deleteClient(id)
}