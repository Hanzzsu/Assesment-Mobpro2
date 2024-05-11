package org.d3if3062.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.d3if3062.mobpro1.database.laundryShopDao
import org.d3if3062.mobpro1.model.Laundry

class MainViewModel(val dao: laundryShopDao) : ViewModel() {

    val data: StateFlow<List<Laundry>> = dao.getLaundryItem().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    suspend fun getItemById(id: Long): Laundry? {
        return dao.getLaundryItemById(id)
    }

    fun insertData(nama: String, jenis: String, jumlah: String) {
        val laundry = Laundry(
            nama = nama,
            jenisPakaian = jenis,
            jumlahPakaian = jumlah
        )
        viewModelScope.launch {
            dao.insert(laundry)
        }

    }

    fun updateData(nama : String, jenis: String, jumlah: String, id : Long){
        val laundry = Laundry(
            id = id,
            nama = nama,
            jenisPakaian = jenis,
            jumlahPakaian = jumlah
        )
        viewModelScope.launch {
            dao.update(laundry)
        }
    }

    fun deleteDataById(id: Long){
        viewModelScope.launch {
            dao.deleteLaundryOrdersById(id)
        }
    }

}