package madengineer.android.mymaterialdesign.ui.main.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import madengineer.android.mymaterialdesign.BuildConfig
import madengineer.android.mymaterialdesign.ui.main.model.EarthEpicServerResponseData
import madengineer.android.mymaterialdesign.ui.main.model.MarsPhotosServerResponseData
import madengineer.android.mymaterialdesign.ui.main.model.PODServerResponseData
import madengineer.android.mymaterialdesign.ui.main.model.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel(
    private val liveDataForViewToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveDataForViewToObserver
    }

    // Picture Of the Day
    fun getPOD(day: Int) {
        val date = getDate(day)
        liveDataForViewToObserver.postValue(AppState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppState.Error(Throwable(API_ERROR))
        } else {
            retrofitImpl.getPOD(apiKey, date, PODCallback)
        }
    }

    private val PODCallback = object : Callback<PODServerResponseData> {
        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserver.postValue(AppState.SuccessPOD(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserver.postValue(AppState.Error(Throwable(UNKNOW_ERROR)))
                } else {
                    liveDataForViewToObserver.postValue(AppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataForViewToObserver.postValue(AppState.Error(t))
        }
    }

    fun getMarsPicture() {
        liveDataForViewToObserver.postValue(AppState.Loading)
        val earthDate = getDayBeforeYesterday()
        retrofitImpl.getMarsPictureByDate(earthDate, BuildConfig.NASA_API_KEY, marsCallback)
    }

    val marsCallback = object : Callback<MarsPhotosServerResponseData> {
        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserver.postValue(AppState.SuccessMars(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserver.postValue(AppState.Error(Throwable(UNKNOW_ERROR)))
                } else {
                    liveDataForViewToObserver.postValue(AppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveDataForViewToObserver.postValue(AppState.Error(t))
        }
    }

    private fun getDayBeforeYesterday(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(2)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val calendar: Calendar = Calendar.getInstance()
            val sDF = SimpleDateFormat("yyyy-MM-dd")
            calendar.add(Calendar.DAY_OF_YEAR, -2)
            return sDF.format(calendar.time)
        }
    }

    private fun getDate(day: Int): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(day.toLong())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val calendar: Calendar = Calendar.getInstance()
            val sDF = SimpleDateFormat("yyyy-MM-dd")
            calendar.add(Calendar.DAY_OF_YEAR, (-day))
            return sDF.format(calendar.time)
        }
    }

    // Earth Polychromatic Imaging Camera
    fun getEpic() {
        liveDataForViewToObserver.postValue(AppState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppState.Error(Throwable(API_ERROR))
        } else {
            retrofitImpl.getEPIC(apiKey, epicCallback)
        }
    }

    private val epicCallback = object : Callback<List<EarthEpicServerResponseData>> {
        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserver.postValue(AppState.SuccessEarthEpic(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserver.postValue(AppState.Error(Throwable(UNKNOW_ERROR)))
                } else {
                    liveDataForViewToObserver.postValue(AppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveDataForViewToObserver.postValue(AppState.Error(t))
        }
    }


    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOW_ERROR = "Unidentified error"
    }

}