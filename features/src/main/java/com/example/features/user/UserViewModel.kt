package com.example.features.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.model.network.ApiUser
import com.example.core.network.ApiInterface
import com.example.features.model.SettingItem
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(
    private val apiInterface: ApiInterface
) : BaseViewModel() {

    val listData = MutableLiveData<ArrayList<SettingItem>>(arrayListOf())

    val listUser = MutableLiveData<List<ApiUser>>(arrayListOf())

    init {
        fetchListData()
    }

    private fun fetchListData() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                listUser.value = apiInterface.getUsers()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun onChooseCheckbox(position: Int, isChecked: Boolean) {

        if (listData.value!![position].isSelected != isChecked) {
            val settingItem = listData.value!![position].let {
                it.copy(name = it.name, isSelected = isChecked, isSelectedEnd = isChecked)
            }
            listData.value!![position] = settingItem
            listData.value = listData.value
        }
    }

    fun onClear(position: Int) {

        listData.value?.removeAt(position)
        listData.value = listData.value
    }
}