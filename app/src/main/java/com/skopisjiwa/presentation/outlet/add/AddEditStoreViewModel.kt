package com.skopisjiwa.presentation.outlet.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skopisjiwa.data.repository.admin.AdminRepository
import com.skopisjiwa.data.store.StoreModel
import com.skopisjiwa.presentation.user.store.adapter.StoreAdapter
import com.skopisjiwa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditStoreViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {


    private val _editStore = MutableLiveData<Resource<String>>()
    val editStore: LiveData<Resource<String>>
        get() = _editStore

    private val _storeUi = MutableLiveData<Resource<String>>()

    private val _store = MutableLiveData<Resource<List<StoreModel>>>()
    val store: LiveData<Resource<String>>
        get() = _storeUi

    val storeAdapter: MutableLiveData<StoreAdapter> = MutableLiveData()


    fun addStore(
        image: String,
        nameStore: String,
        jamOperation: String,
        addressStore: String,
        uiState: () -> Unit
    ) {
        return adminRepository.addStore(image, nameStore, jamOperation, addressStore, uiState)
    }

    fun updateStore(
        arrayList: StoreModel,
        id: String,
        ) {
        viewModelScope.launch {
            adminRepository.editStore(id, arrayList) {
                _editStore.value = it
            }
        }
    }

    fun getStore(
        arrayList: MutableList<StoreModel>,
        storeAdapter: StoreAdapter
    ) {
        viewModelScope.launch {
            _store.value = Resource.Loading
            adminRepository.getStore(
                arrayList,
                storeAdapter
            ) { resource ->
                _store.value = resource
            }
        }
    }
}