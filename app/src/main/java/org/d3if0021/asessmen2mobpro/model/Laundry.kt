package org.d3if3062.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Laundry")
data class Laundry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val jenisPakaian: String,
    val jumlahPakaian: String
)