package com.yotfr.testapp.ui.screens.cameras

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yotfr.testapp.data.repository.cameras.CamerasRepository
import com.yotfr.testapp.data.util.Response
import com.yotfr.testapp.ui.screens.cameras.event.CamerasEvent
import com.yotfr.testapp.ui.screens.cameras.mapper.toCameraRealm
import com.yotfr.testapp.ui.screens.cameras.mapper.toRooms
import com.yotfr.testapp.ui.screens.cameras.model.CamerasState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CamerasViewModel @Inject constructor(
    private val camerasRepository: CamerasRepository
) : ViewModel() {

    private val _isForceUpdating = MutableStateFlow(false)

    private val _state = MutableLiveData(CamerasState())
    val state: LiveData<CamerasState> = _state

    init {
        viewModelScope.launch {
            _isForceUpdating.collectLatest {
                camerasRepository.getCamerasData(
                    forceUpdate = _isForceUpdating.value
                ).collect { response ->
                    Log.d("TESTRESPONSE", "response -> $response")
                    when (response) {
                        is Response.Loading -> {
                            _state.value = _state.value?.copy(
                                isLoading = true
                            )
                        }
                        is Response.Success -> {
                            _state.value = _state.value?.copy(
                                isLoading = false,
                                rooms = response.data?.toRooms() ?: throw IllegalArgumentException(
                                    "data cannot be null if response is Response.Success"
                                )
                            )
                            _isForceUpdating.value = false
                        }
                        is Response.Exception -> {
                            Log.d("TEST", "error")
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

    fun onEvent(event: CamerasEvent) {
        when (event) {
            CamerasEvent.Swiped -> {
                _isForceUpdating.value = true
            }
            is CamerasEvent.FavoriteClicked -> {
                viewModelScope.launch {
                    camerasRepository.updateCamera(
                        cameraRealm = event.camera.copy(
                            isFavorite = !event.camera.isFavorite
                        ).toCameraRealm()
                    )
                }
            }
        }
    }
}
