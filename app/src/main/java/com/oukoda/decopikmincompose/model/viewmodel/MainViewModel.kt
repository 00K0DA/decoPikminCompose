package com.oukoda.decopikmincompose.model.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oukoda.decopikmincompose.model.dataclass.PikminCostumeList
import com.oukoda.decopikmincompose.model.dataclass.PikminData
import com.oukoda.decopikmincompose.model.dataclass.PikminDataList
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _decors: MutableStateFlow<List<PikminCostumeList>> = MutableStateFlow(listOf())
    val decors: StateFlow<List<PikminCostumeList>> = _decors

    fun createDecors() {
        viewModelScope.launch(Dispatchers.IO) {
            for (decorType in DecorType.values()) {
                val pikminDataLists = decorType.getCostumes().map { costumeType ->
                    val pikminCountByColor =
                        PikminType.values().associateWith { 0 }.toMutableMap()
                    PikminDataList(
                        costumeType = costumeType,
                        pikminDataList =
                        costumeType.getPikminList().map { pikminType ->
                            val number = pikminCountByColor[pikminType]!!
                            pikminCountByColor[pikminType] = number + 1
                            PikminData.newInstance(pikminType, number)
                        },
                    )
                }
                val pikminCostumeList =
                    PikminCostumeList(
                        decorType = decorType,
                        pikminDataLists = pikminDataLists,
                    )
                Log.d("TAG", "onCreate: $decorType")
                val temp = _decors.value.toMutableList()
                temp.add(pikminCostumeList)
                viewModelScope.launch(Dispatchers.Main) { _decors.value = temp.toList() }
                delay(250)
            }
        }
    }
}
