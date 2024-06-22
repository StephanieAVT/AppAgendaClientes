package com.example.crudmaxprocess.data.mapper

import com.example.crudmaxprocess.data.database.entity.ClientEntity
import com.example.crudmaxprocess.domain.model.Client

fun ClientEntity.toDomain() : Client {
    return Client(
        id = this.id,
        cpf = this.cpf,
        name = this.name,
        createdAt = this.createdAt,
        birthDate = this.birthDate.toString(),
        uf = this.uf,
        phone = this.phone,
        phone2 = this.phone2
    )
}

fun Client.toEntity() : ClientEntity {
    return ClientEntity(
        id = this.id,
        cpf = this.cpf,
        name = this.name,
        createdAt = this.createdAt,
        birthDate = this.birthDate,
        uf = this.uf,
        phone = this.phone,
        phone2 = this.phone2
    )
}