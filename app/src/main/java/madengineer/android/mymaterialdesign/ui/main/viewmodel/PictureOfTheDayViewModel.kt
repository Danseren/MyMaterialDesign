package madengineer.android.mymaterialdesign.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import madengineer.android.mymaterialdesign.BuildConfig
import madengineer.android.mymaterialdesign.ui.main.model.PODServerResponseData
import madengineer.android.mymaterialdesign.ui.main.model.PODRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserver: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<PictureOfTheDayData> {
        sendServerRequest()
        return liveDataForViewToObserver
    }

    private fun sendServerRequest() {
        liveDataForViewToObserver.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserver.value =
                            PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserver.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserver.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserver.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }
}