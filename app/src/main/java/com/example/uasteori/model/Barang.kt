package com.example.uasteori.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "barangs")
@Parcelize
data class Barang(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "gambar_barang")
    var gambar_barang: String = "",

    @ColumnInfo(name = "nama_barang")
    var nama_barang: String = "",

    @ColumnInfo(name = "harga_barang")
    var harga_barang: Double = 0.0,

    @ColumnInfo(name = "stok")
    var stok: Int = 0,

    @ColumnInfo(name = "deskripsi")
    var deskripsi: String = ""
) : Parcelable
