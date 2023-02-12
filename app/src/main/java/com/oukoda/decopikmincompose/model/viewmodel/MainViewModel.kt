package com.oukoda.decopikmincompose.model.viewmodel

import android.app.Application
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    companion object {
        private val TAG: String = MainViewModel::class.simpleName!!
        const val KEY: String = "MainViewModel"
    }

    private val _showComplete: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showComplete: StateFlow<Boolean> = _showComplete

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _allDecorGroups: MutableStateFlow<List<DecorGroup>> = MutableStateFlow(listOf())
    private val allDecorGroups: StateFlow<List<DecorGroup>> = _allDecorGroups

    val normalDecorGroups: StateFlow<List<DecorGroup>> = allDecorGroups.map { groups ->
        groups.filter { it.decorType != DecorType.Special }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        listOf(),
    )

    val showNormalDecorGroups: StateFlow<List<DecorGroup>> =
        normalDecorGroups.combine(showComplete) { decorGroups, show ->
            if (show) decorGroups else decorGroups.filter { !it.isCompleted() }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            listOf(),
        )

    val specialDecorGroup: StateFlow<DecorGroup> = allDecorGroups.transform { groups ->
        if (groups.any { it.decorType == DecorType.Special }) {
            emit(groups.first { it.decorType == DecorType.Special })
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        DecorGroup(decorType = DecorType.Special, listOf()),
    )

    private var appDatabase: AppDatabase

    init {
        appDatabase = AppDatabase.getInstance(application)
    }

    fun updateShowComplete(boolean: Boolean) {
        _showComplete.value = boolean
    }

    fun createDecors() {
        if (normalDecorGroups.value.isNotEmpty()) {
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

                val mutableDecorGroups = _allDecorGroups.value.toMutableList()
                mutableDecorGroups.add(decorGroup)

                viewModelScope.launch(Dispatchers.Main) {
                    _allDecorGroups.value = mutableDecorGroups.toList()
                }.join()
            }
            viewModelScope.launch(Dispatchers.Main) {
                _isLoading.value = false
            }
        }
    }

    fun updatePikminRecord(pikminRecord: PikminRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            _allDecorGroups.update { groups ->
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
