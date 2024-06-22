package com.example.crudmaxprocess.di

import androidx.room.Room
import com.example.crudmaxprocess.data.database.AppDatabase
import com.example.crudmaxprocess.data.repository.ClientRepositoryImpl
import com.example.crudmaxprocess.domain.repository.ClientRepository
import com.example.crudmaxprocess.domain.usecase.DeleteClientUseCase
import com.example.crudmaxprocess.domain.usecase.GetClientsUseCase
import com.example.crudmaxprocess.domain.usecase.InsertClientUseCase
import com.example.crudmaxprocess.domain.usecase.UpdateClientUseCase
import com.example.crudmaxprocess.presentation.clientslist.ClientsListViewModel
import com.example.crudmaxprocess.presentation.saveclients.SaveClientsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        SaveClientsViewModel(
            saveClientsUseCase =
            InsertClientUseCase(clientRepository = get()),
            updateClientUseCase = UpdateClientUseCase(clientRepository = get())
        )
    }

    viewModel {
        ClientsListViewModel(
            getClientsUseCase =
            GetClientsUseCase(clientRepository = get()),
            deleteClientUseCase = DeleteClientUseCase(
                clientRepository = get()
            )
        )
    }
}

val storageModule = module {
    factory<ClientRepository> {
        ClientRepositoryImpl(
            clientDAO = get()
        )
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "client_database_app.db"
        ).build()
    }
    single {
        get<AppDatabase>().clientDao()
    }

}
