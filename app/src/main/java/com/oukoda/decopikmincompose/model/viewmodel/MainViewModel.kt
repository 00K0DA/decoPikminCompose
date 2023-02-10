package com.oukoda.decopikmincompose.model.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.room.AppDatabase
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    companion object {
        private val TAG: String = MainViewModel::class.simpleName!!
    }

    private val _decorGroups: MutableStateFlow<List<DecorGroup>> = MutableStateFlow(listOf())
    val decorGroups: StateFlow<List<DecorGroup>> = _decorGroups
    private val _showDecorGroups: MutableStateFlow<List<DecorGroup>> = MutableStateFlow(listOf())
    val showDecorGroups: StateFlow<List<DecorGroup>> = _showDecorGroups
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _showComplete: MutableLiveData<Boolean> = MutableLiveData(true)
    val showComplete: LiveData<Boolean> = _showComplete
    private var appDatabase: AppDatabase

    init {
        appDatabase = AppDatabase.getInstance(application)
    }

    fun updateShowComplete(boolean: Boolean) {
        _showComplete.value = boolean
        updateShowDecorGroups()
    }

    private fun updateShowDecorGroups() {
        _showDecorGroups.value = if (_showComplete.value!!) {
            decorGroups.value
        } else {
            decorGroups.value.filter { !it.isCompleted() }
        }
    }

    fun createDecors() {
        if (decorGroups.value.isNotEmpty()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val pikminRecordDao = appDatabase.pikminRecordDao()
            for (decorType in DecorType.values()) {
                // PikminRecordをDecorTypeごとに取得
                val pikminRecords = pikminRecordDao.selectByDecorType(decorType)
                val mutableCostumeGroups: MutableList<CostumeGroup> = mutableListOf()
                // Costumeごとに処理を行う
                for (costumeType in decorType.costumeTypes()) {
                    val filteredRecords = pikminRecords.filter { it.costumeType == costumeType }
                    val initialCostumeGroup = CostumeGroup.newInstance(costumeType)
                    val (insertRecords, deleteRecords) = initialCostumeGroup.compareToPikminRecords(
                        decorType,
                        filteredRecords,
                    )
                    // 不足しているデータをDBに追加
                    for (record in insertRecords) {
                        pikminRecordDao.insert(record)
                    }

                    // 余分なデータをDBから削除
                    for (record in deleteRecords) {
                        pikminRecordDao.delete(record)
                    }

                    // recordsを反映させたcostumeGroupの作成
                    mutableCostumeGroups.add(
                        initialCostumeGroup.updateByPikminRecords(
                            filteredRecords,
                        ),
                    )
                }

                val decorGroup = DecorGroup(
                    decorType = decorType,
                    costumeGroups = mutableCostumeGroups.toList(),
                )

                val mutableDecorGroups = _decorGroups.value.toMutableList()
                mutableDecorGroups.add(decorGroup)

                viewModelScope.launch(Dispatchers.Main) {
                    _decorGroups.value = mutableDecorGroups.toList()
                }.join()
            }
            Log.d(TAG, "createDecors: ${_decorGroups.value.size}")
            updateShowDecorGroups()
            viewModelScope.launch(Dispatchers.Main) {
                _isLoading.value = false
            }
        }
    }

    fun updatePikminRecord(pikminRecord: PikminRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            _decorGroups.update { groups ->
                groups.map {
                    if (it.decorType == pikminRecord.decorType) {
                        var costumeGroup =
                            it.first { costumeGroup -> costumeGroup.costumeType == pikminRecord.costumeType }
                        costumeGroup = costumeGroup.updateByPikminRecords(listOf(pikminRecord))
                        it.updateCostumeGroup(costumeGroup)
                    } else {
                        it
                    }
                }
            }
            updateShowDecorGroups()
            appDatabase.pikminRecordDao().update(pikminRecord)
        }
    }

    class MainViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(application) as T
        }
    }
}
