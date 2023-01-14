package com.oukoda.decopikmincompose.model.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    companion object {
        private val TAG: String = MainViewModel::class.simpleName!!
    }
    private val _decors: MutableStateFlow<List<DecorGroup>> = MutableStateFlow(listOf())
    val decors: StateFlow<List<DecorGroup>> = _decors

    fun createDecors() {
        viewModelScope.launch(Dispatchers.IO) {
            for (decorType in DecorType.values()) {
                val costumeGroups = decorType.getCostumes().map { costumeType ->
                    val pikminCountByColor =
                        PikminType.values().associateWith { 0 }.toMutableMap()
                    CostumeGroup(
                        costumeType = costumeType,
                        pikminIdentifiers =
                        costumeType.getPikminList().map { pikminType ->
                            val number = pikminCountByColor[pikminType]!!
                            pikminCountByColor[pikminType] = number + 1
                            PikminIdentifier.newInstance(pikminType, number)
                        },
                    )
                }
                val decorGroup = DecorGroup(
                    decorType = decorType,
                    costumeGroups = costumeGroups,
                )
                Log.d(TAG, "onCreate: $decorType")
                val mutableDecors = _decors.value.toMutableList()
                mutableDecors.add(decorGroup)
                viewModelScope.launch(Dispatchers.Main) { _decors.value = mutableDecors.toList() }
                delay(250)
            }
        }
    }
}
