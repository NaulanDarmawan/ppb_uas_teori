package com.example.uasteori.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uasteori.databinding.ItemBarangBinding
import com.example.uasteori.model.Barang
import com.squareup.picasso.Picasso
import java.io.File

class BarangAdapter(
    private val onEdit: (Barang) -> Unit,
    private val onDelete: (Barang) -> Unit
) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    private var barangList = listOf<Barang>()

    // Fungsi untuk memperbarui data barang
    fun updateData(newBarangList: List<Barang>) {
        barangList = newBarangList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        // Menggunakan view binding untuk menghubungkan dengan layout item_barang
        val binding = ItemBarangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BarangViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangList[position]
        holder.bind(barang)
    }

    override fun getItemCount(): Int = barangList.size

    inner class BarangViewHolder(private val binding: ItemBarangBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(barang: Barang) {
            // Set data barang pada tampilan
            binding.tvNamaBarang.text = barang.nama_barang
            binding.tvHargaBarang.text = "Rp ${barang.harga_barang}"
            binding.tvStok.text = "Stok: ${barang.stok}"

            // Set gambar barang dengan Picasso
            val imageFile = File(binding.root.context.filesDir, barang.gambar_barang)
            if (imageFile.exists()) {
                Picasso.get().load(imageFile).into(binding.ivBarang)
            }

            // Set aksi untuk tombol edit dan delete
            binding.btnEdit.setOnClickListener { onEdit(barang) }
            binding.btnDelete.setOnClickListener { onDelete(barang) }
        }
    }
}
