package com.example.uasteori.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.uasteori.model.Barang

@Dao
interface BarangDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barang: Barang)

    @Update
    suspend fun update(barang: Barang)

    @Delete
    suspend fun delete(barang: Barang)

    @Query("SELECT * FROM barangs")
    fun getAll(): LiveData<List<Barang>>

    @Query("SELECT * FROM barangs WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Barang?
}
