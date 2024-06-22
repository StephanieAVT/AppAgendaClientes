package com.example.crudmaxprocess.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.crudmaxprocess.data.database.entity.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(clientEntity: ClientEntity): Long

    @Upsert
    suspend fun update(clientEntity: ClientEntity)

    @Query("DELETE FROM client WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM client")
    suspend fun deleteAll()

    @Query("SELECT * FROM client")
    fun getAllClients(): Flow<List<ClientEntity>>


}