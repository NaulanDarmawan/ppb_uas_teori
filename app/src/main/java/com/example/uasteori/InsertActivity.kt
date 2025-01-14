package com.example.uasteori

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.uasteori.databinding.ActivityInsertBinding
import com.example.uasteori.model.Barang
import com.example.uasteori.viewmodel.BarangViewModel
import com.squareup.picasso.Picasso
import java.util.*

class InsertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertBinding
    private lateinit var barangViewModel: BarangViewModel
    private var barang: Barang? = null
    private var selectedImageName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        barangViewModel = ViewModelProvider(this).get(BarangViewModel::class.java)

        barang = intent.getParcelableExtra("barang")
        setupForm()
        setupListeners()
    }

    private fun setupForm() {
        barang?.let {
            binding.etNamaBarang.setText(it.nama_barang)
            binding.etHargaBarang.setText(it.harga_barang.toString())
            binding.etStok.setText(it.stok.toString())
            binding.etDeskripsi.setText(it.deskripsi)

            selectedImageName = it.gambar_barang
            val resId = resources.getIdentifier(it.gambar_barang, "drawable", packageName)
            binding.ivGambarBarang.setImageResource(resId)
        }
    }

    private fun setupListeners() {
        binding.btnUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        binding.btnSave.setOnClickListener {
            saveBarang()
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let {
                val imageName = "sample_image_${UUID.randomUUID()}"
                selectedImageName = imageName
                Picasso.get().load(it).into(binding.ivGambarBarang)
                Toast.makeText(this, "Gambar berhasil diupload", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBarang() {
        val namaBarang = binding.etNamaBarang.text.toString()
        val hargaBarang = binding.etHargaBarang.text.toString().toDoubleOrNull() ?: 0.0
        val stok = binding.etStok.text.toString().toIntOrNull() ?: 0
        val deskripsi = binding.etDeskripsi.text.toString()

        if (namaBarang.isBlank() || selectedImageName.isNullOrBlank()) {
            Toast.makeText(this, "Nama barang dan gambar harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val barangToSave = barang?.copy(
            nama_barang = namaBarang,
            harga_barang = hargaBarang,
            stok = stok,
            deskripsi = deskripsi,
            gambar_barang = selectedImageName!!
        ) ?: Barang(
            nama_barang = namaBarang,
            harga_barang = hargaBarang,
            stok = stok,
            deskripsi = deskripsi,
            gambar_barang = selectedImageName!!
        )

        if (barang != null) {
            barangViewModel.updateBarang(barangToSave)
            Toast.makeText(this, "Barang berhasil diupdate", Toast.LENGTH_SHORT).show()
        } else {
            barangViewModel.insertBarang(barangToSave)
            Toast.makeText(this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }

        barangViewModel.allBarangs.observe(this, { barangs ->
        // Update RecyclerView data here
        //barangAdapter.submitList(barangs)
        })

        finish()
    }
}
