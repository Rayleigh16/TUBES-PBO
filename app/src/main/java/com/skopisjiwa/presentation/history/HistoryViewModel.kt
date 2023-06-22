package com.skopisjiwa.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skopisjiwa.data.bill.HistoryTransactionModel
import com.skopisjiwa.data.bill.repository.MitraRepository
import com.skopisjiwa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val mitraRepository: MitraRepository
) : ViewModel() {


    private val _history = MutableLiveData<Resource<String>>()

    private val transaction = MutableLiveData<Resource<List<HistoryTransactionModel>>>()
    val kegiatan: LiveData<Resource<String>>
        get() = _history

    fun getDataKegiatan(
        arrayList: ArrayList<HistoryTransactionModel>, adapter: HistoryAdapter
    ) {
        viewModelScope.launch {
            transaction.value = Resource.Loading
            mitraRepository.getTransaction(
                arrayList, adapter
            ) {
                transaction.value = it
            }
        }
    }
}