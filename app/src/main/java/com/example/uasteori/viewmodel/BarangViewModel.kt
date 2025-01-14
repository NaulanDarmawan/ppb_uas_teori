package com.example.uasteori.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.uasteori.database.AppDatabase
import com.example.uasteori.model.Barang
import kotlinx.coroutines.launch

class BarangViewModel(application: Application) : AndroidViewModel(application) {

    private val barangDao = AppDatabase.getDatabase(application).barangDao()

    // Properti untuk mengambil semua barang
    val allBarangs: LiveData<List<Barang>> = barangDao.getAll()

    // Fungsi untuk menambahkan barang
    fun insertBarang(barang: Barang) {
        viewModelScope.launch {
            barangDao.insert(barang)
        }
    }

    // Fungsi untuk memperbarui barang
    fun updateBarang(barang: Barang) {
        viewModelScope.launch {
            barangDao.update(barang)
        }
    }

    // Fungsi untuk menghapus barang
    fun deleteBarang(barang: Barang) {
        viewModelScope.launch {
            barangDao.delete(barang)
        }
    }
}
