package com.example.crudmaxprocess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client (
    val id: Long = 0,
    val cpf: String?,
    val name: String,
    val createdAt: String,
    val birthDate: String?,
    val uf: String?,
    val phone: String?,
    val phone2: String?
) : Parcelable