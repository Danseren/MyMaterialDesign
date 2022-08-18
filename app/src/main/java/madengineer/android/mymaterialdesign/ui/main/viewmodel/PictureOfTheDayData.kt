package madengineer.android.mymaterialdesign.ui.main.viewmodel

import madengineer.android.mymaterialdesign.ui.main.model.PODServerResponseData

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}