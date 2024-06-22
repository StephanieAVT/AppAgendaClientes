package com.example.crudmaxprocess.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "client")
data class ClientEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cpf: String?,
    val name: String,
    val createdAt:  String,
    val birthDate: String?,
    val uf: String?,
    val phone: String?,
    val phone2: String?,
)