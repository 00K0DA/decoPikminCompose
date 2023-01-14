package com.oukoda.decopikmincompose.model.viewmodel

import android.app.Application
import android.util.Log
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
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    companion object {
        private val TAG: String = MainViewModel::class.simpleName!!
    }

    private val _decorGroups: MutableStateFlow<List<DecorGroup>> = MutableStateFlow(listOf())
    val decorGroups: StateFlow<List<DecorGroup>> = _decorGroups
    private var appDatabase: AppDatabase

    init {
        appDatabase = AppDatabase.getInstance(application)
    }

    fun createDecors() {
        viewModelScope.launch(Dispatchers.IO) {
            val pikminRecordDao = appDatabase.pikminRecordDao()
            for (decorType in DecorType.values()) {
                Log.d(TAG, "createDecors: $decorType")
                // PikminRecordをDecorTypeごとに取得
                val pikminRecords = pikminRecordDao.selectByDecorType(decorType)
                Log.d(TAG, "createDecors: $pikminRecords")
                val mutableCostumeGroups: MutableList<CostumeGroup> = mutableListOf()
                // Costumeごとに処理を行う
                for (costumeType in decorType.getCostumes()) {
                    val filteredRecords = pikminRecords.filter { it.costumeType == costumeType }
                    Log.d(TAG, "createDecors: filteredRecords:$filteredRecords")
                    val initialCostumeGroup = CostumeGroup.newInstance(costumeType)
                    initialCostumeGroup.forEach {
                        Log.d(TAG, "createDecors: initialCostumeGroup: $it")
                    }
                    val (insertRecords, deleteRecords) = initialCostumeGroup.compareToPikminRecords(
                        decorType,
                        filteredRecords,
                    )
                    Log.d(TAG, "createDecors: insertRecords: $insertRecords")
                    Log.d(TAG, "createDecors: deleteRecords: $deleteRecords")
                    // 不足しているデータをDBに追加
                    for (record in insertRecords) {
                        pikminRecordDao.insert(record)
                    }

                    // 余分なデータをDBから削除
                    for (record in deleteRecords) {
                        pikminRecordDao.delete(record)
                    }

                    // recordsを反映させたcostumeGroupの作成
                    mutableCostumeGroups.add(initialCostumeGroup.applyPikminRecords(filteredRecords))
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
        }
    }

    fun updatePikminRecord(pikminRecord: PikminRecord) {
        viewModelScope.launch(Dispatchers.IO) {
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
