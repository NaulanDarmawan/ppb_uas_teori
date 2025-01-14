package com.example.uasteori

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uasteori.adapter.BarangAdapter
import com.example.uasteori.databinding.ActivityMainBinding
import com.example.uasteori.model.Barang
import com.example.uasteori.viewmodel.BarangViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var barangViewModel: BarangViewModel
    private lateinit var barangAdapter: BarangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        barangViewModel = ViewModelProvider(this).get(BarangViewModel::class.java)

        // Setup RecyclerView
        setupRecyclerView()

        // Observe LiveData
        barangViewModel.allBarangs.observe(this) { barangs ->
            barangAdapter.updateData(barangs)  // Memperbarui RecyclerView dengan data barang
        }

        // Floating Action Button
        binding.fab.setOnClickListener {
            navigateToInsertActivity()
        }
    }

    private fun setupRecyclerView() {
        barangAdapter = BarangAdapter(
            onEdit = { barang -> navigateToInsertActivity(barang) },
            onDelete = { barang -> confirmDelete(barang) }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = barangAdapter
        }
    }

    private fun navigateToInsertActivity(barang: Barang? = null) {
        val intent = Intent(this, InsertActivity::class.java).apply {
            putExtra("barang", barang)
        }
        startActivity(intent)
    }

    private fun confirmDelete(barang: Barang) {
        AlertDialog.Builder(this).apply {
            setTitle("Hapus Barang")
            setMessage("Apakah Anda yakin ingin menghapus ${barang.nama_barang}?")
            setPositiveButton("Ya") { _, _ ->
                barangViewModel.deleteBarang(barang)
                Toast.makeText(this@MainActivity, "Barang berhasil dihapus", Toast.LENGTH_SHORT)
                    .show()
            }
            setNegativeButton("Tidak", null)
            show()
        }
    }
}
