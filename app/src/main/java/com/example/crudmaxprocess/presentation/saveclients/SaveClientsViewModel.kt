package com.example.crudmaxprocess.presentation.saveclients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.crudmaxprocess.domain.model.Client
import com.example.crudmaxprocess.domain.usecase.InsertClientUseCase
import com.example.crudmaxprocess.domain.usecase.UpdateClientUseCase
import com.example.crudmaxprocess.presentation.utils.extensions.toDate
import com.example.crudmaxprocess.presentation.utils.state.StateView
import kotlinx.coroutines.Dispatchers
import java.util.Calendar

internal class SaveClientsViewModel(
    private val saveClientsUseCase: InsertClientUseCase,
    private val updateClientUseCase: UpdateClientUseCase
) :
    ViewModel() {

    private var _messageEventData = MutableLiveData<String>()
    val messageEventData: LiveData<String> = _messageEventData



    fun saveClient(client: Client) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            if (!validateClientData(client)) {
                emit(StateView.Error(message = _messageEventData.value))
                return@liveData
            }

            val id = saveClientsUseCase(client)
            emit(StateView.Success(id))


        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

    fun updateClient(client: Client) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val id = updateClientUseCase(client)
            emit(StateView.Success(id))


        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

    private fun validateClientData(client: Client): Boolean {
        return verifyRules(client)
    }

    private fun verifyRules(client: Client): Boolean {
        return if (client.name.isNullOrEmpty() || client.birthDate == null || client.uf.isNullOrEmpty()) {
            false
        } else if (client.uf == "SP" && client.cpf.isNullOrEmpty()) {
            _messageEventData.postValue("O preenchimento do CPF nesse caso é obrigatorio")
            false
        } else if (client.uf == "MG" && calculateAge(client.birthDate) < 18) {
            _messageEventData.postValue("O cliente deve ser maior de idade")
            false
        } else {
            true
        }
    }

    private fun calculateAge(birthDate: String): Int {

        val dataNascimentoCal = Calendar.getInstance()
        dataNascimentoCal.time = birthDate.toDate()

        val hoje = Calendar.getInstance()

        var idade = hoje.get(Calendar.YEAR) - dataNascimentoCal.get(Calendar.YEAR)

        if (hoje.get(Calendar.DAY_OF_YEAR) < dataNascimentoCal.get(Calendar.DAY_OF_YEAR)) {
            idade--
        }
        return idade
    }

}

