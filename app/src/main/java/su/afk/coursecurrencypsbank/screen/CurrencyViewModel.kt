package su.afk.coursecurrencypsbank.screen

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import su.afk.coursecurrencypsbank.R
import su.afk.coursecurrencypsbank.data.CourceRepository
import su.afk.coursecurrencypsbank.data.retrofit.ApiService
import su.afk.coursecurrencypsbank.data.models.Currency
import su.afk.coursecurrencypsbank.util.Resource
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CourceRepository)
    : ViewModel() {

    // цвет текста в вверху экрана
    val textColor = MutableLiveData<Int>()

    // LiveData, содержащий список валют
    val currencies = MutableLiveData<List<Currency>>()

    // проверка на статус загрузки
    val isLoading = MutableLiveData(false)

    // LiveData, содержащий текст для обновления даты
    val updateDateText = MutableLiveData<String>()

    // функция форматирования времени
    fun formatDate(): String {
        val currentDate = Calendar.getInstance().time
        val outputFormat = SimpleDateFormat("dd.MM.yyyy в HH:mm", Locale.getDefault())
        return outputFormat.format(currentDate)
    }

    init {
        loadData() //подгряжаем данные при создае ViewModel
    }
    // Функция для загрузки данных
    fun loadData() {
        isLoading.value = true

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val currencyResponse = repository.getCource() // Получаем данные с API

                when(currencyResponse) { // проверяем возвращаемый тип
                    is Resource.Success -> {
                        currencies.postValue(currencyResponse.data?.Valute?.values?.toList())
                        isLoading.postValue(false) // Обновляем LiveData с новыми данными о валютах
                        // Обновляем текст и цвет для даты
                        updateDateText.postValue("Последние данные: ${formatDate()}")
                        textColor.postValue(R.color.text_value_color)

//                        // После завершения загрузки данных скрываем ProgressBar
//                        progressBarVisibility.postValue(View.GONE)
                    }
                    is Resource.Error -> {
                        // Обновляем текст даты об ошибке и меняем цвет
                        updateDateText.postValue("Не удалось получить новые данные, попробуйте позднее")
                        textColor.postValue(R.color.red)
                        isLoading.postValue(false)
                    }
                    is Resource.Loading -> {
                    }
                }
                delay(30000) //повторяем каждые 30 секунд
            }
        }
    }
}