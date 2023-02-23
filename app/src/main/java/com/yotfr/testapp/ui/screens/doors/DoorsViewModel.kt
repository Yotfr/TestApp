package com.yotfr.testapp.ui.screens.doors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yotfr.testapp.data.repository.doors.DoorsRepository
import com.yotfr.testapp.data.util.Response
import com.yotfr.testapp.ui.screens.doors.event.DoorsEvent
import com.yotfr.testapp.ui.screens.doors.mapper.toDoorRealm
import com.yotfr.testapp.ui.screens.doors.mapper.toDoorUi
import com.yotfr.testapp.ui.screens.doors.model.DoorsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoorsViewModel @Inject constructor(
    private val doorsRepository: DoorsRepository
) : ViewModel() {

    private val _isForceUpdating = MutableStateFlow(false)

    private val _state = MutableLiveData(DoorsState())
    val state: LiveData<DoorsState> = _state

    init {
        viewModelScope.launch {
            _isForceUpdating.collectLatest {
                doorsRepository.getDoorsData(
                    forceUpdate = it
                ).collectLatest { response ->
                    Log.d("TESTTT", "response -> $response")
                    when (response) {
                        is Response.Loading -> {
                            _state.value = _state.value?.copy(
                                isLoading = true
                            )
                        }
                        is Response.Success -> {
                            Log.d("TESTTT", "response data -> ${response.data}")
                            _state.value = _state.value?.copy(
                                isLoading = false,
                                doors = response.data?.doorRealms?.map { it.toDoorUi() } ?: emptyList()
                            )
                            _isForceUpdating.value = false
                        }
                        is Response.Exception -> {
                            _state.value = _state.value?.copy(
                                isLoading = false
                            )
                            _isForceUpdating.value = false
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: DoorsEvent) {
        when (event) {
            DoorsEvent.Swiped -> {
                viewModelScope.launch {
                    _isForceUpdating.value = true
                }
            }
            is DoorsEvent.FavoriteClicked -> {
                viewModelScope.launch {
                    doorsRepository.updateDoor(
                        doorRealm = event.door.copy(
                            favorites = !event.door.favorites
                        ).toDoorRealm()
                    )
                }
            }
            is DoorsEvent.EditNameClicked -> {
                viewModelScope.launch {
                    doorsRepository.updateDoor(
                        doorRealm = event.door.copy(
                            name = event.newName
                        ).toDoorRealm()
                    )
                }
            }
        }
    }
}
