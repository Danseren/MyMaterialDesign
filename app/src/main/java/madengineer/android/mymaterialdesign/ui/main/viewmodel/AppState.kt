package madengineer.android.mymaterialdesign.ui.main.viewmodel

import madengineer.android.mymaterialdesign.ui.main.model.EarthEpicServerResponseData
import madengineer.android.mymaterialdesign.ui.main.model.MarsPhotosServerResponseData
import madengineer.android.mymaterialdesign.ui.main.model.PODServerResponseData

sealed class AppState {
    data class SuccessPOD(val serverResponseData: PODServerResponseData) : AppState()
    data class SuccessEarthEpic (val serverResponseData: List<EarthEpicServerResponseData>) : AppState()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}