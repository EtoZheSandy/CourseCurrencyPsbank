package su.afk.coursecurrencypsbank

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import su.afk.coursecurrencypsbank.retrofit.ApiService
import su.afk.coursecurrencypsbank.retrofit.Currency
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CurrencyViewModel : ViewModel() {

    // цвет текста в вверху экрана
    val textColor = MutableLiveData<Int>()

    // LiveData, содержащий список валют
    val currencies = MutableLiveData<List<Currency>>()

    // LiveData, содержащий текст для обновления даты
    val updateDateText = MutableLiveData<String>()

    // Показываем ProgressBar перед загрузкой данных
    val progressBarVisibility = MutableLiveData<Int>()

    fun getApiService(): ApiService {
        //для отладки
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru/").client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        return apiService
    }

    // функция форматирования времени
    fun formatDate(): String {
        val currentDate = Calendar.getInstance().time
        val outputFormat = SimpleDateFormat("dd.MM.yyyy в HH:mm", Locale.getDefault())
        return outputFormat.format(currentDate)
    }


    // Функция для загрузки данных
    fun loadData() {
        // Показываем ProgressBar перед загрузкой данных
        progressBarVisibility.postValue(View.VISIBLE)

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    // Получаем данные с API
                    val currencyResponse = getApiService().getCurrencyData()
                    // Получаем список валют из ответа
                    val currenciesList = currencyResponse.Valute.values.toList()

                    // Обновляем LiveData с новыми данными о валютах
                    currencies.postValue(currenciesList)

                    // Обновляем текст и цвет для даты
                    updateDateText.postValue("Обновлено: ${formatDate()}")
                    textColor.postValue(R.color.black)

                } catch (e: Exception) {
                    // Обновляем текст даты об ошибке и меняем цвет
                    updateDateText.postValue("Не удалось получить новые данные, попробуйте позднее")
                    textColor.postValue(R.color.red)
                } finally {
                    // После завершения загрузки данных скрываем ProgressBar
                    progressBarVisibility.postValue(View.GONE)
                }
                //повторяем каждые 30 секунд
                delay(30000)
            }
        }
    }
}