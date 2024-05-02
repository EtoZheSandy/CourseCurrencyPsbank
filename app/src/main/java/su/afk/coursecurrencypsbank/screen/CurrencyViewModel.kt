package su.afk.coursecurrencypsbank.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import su.afk.coursecurrencypsbank.R
import su.afk.coursecurrencypsbank.data.CourceRepository
import su.afk.coursecurrencypsbank.data.models.Currency
import su.afk.coursecurrencypsbank.screen.models.DateUpdate
import su.afk.coursecurrencypsbank.util.Resource
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CourceRepository)
    : ViewModel() {

    val currencies = MutableLiveData<List<Currency>>() // LiveData, содержащий список валют

    val isLoading = MutableLiveData(false) // проверка на статус загрузки

    val updateDateText = MutableLiveData<DateUpdate>() // LiveData, содержащий текст и цвет для обновления даты

    // функция форматирования времени
    fun formatDate(): String {
        val currentDate = Calendar.getInstance().time
        val outputFormat = SimpleDateFormat("dd.MM.yyyy в HH:mm", Locale.getDefault())
        return outputFormat.format(currentDate)
    }

    init {
        loadData() //подгружаем данные при создание ViewModel
    }

    // Функция для загрузки данных
    fun loadData() {
        isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val currencyResponse = repository.getCource() // Получаем данные с API

                when(currencyResponse) { // проверяем возвращаемый тип
                    is Resource.Success -> {
                        currencies.postValue(currencyResponse.data?.Valute?.values?.toList())

                        isLoading.postValue(false) // Обновляем LiveData с новыми данными о валютах
                        updateDateText.postValue(DateUpdate(date = "Последние доступные данные: ${formatDate()}",
                            colorText = R.color.text_value_color)) // Обновляем текст и цвет для даты
                    }
                    is Resource.Error -> {
                        isLoading.postValue(false)
                        updateDateText.postValue(DateUpdate(date = "Не удалось получить данные, попробуйте позднее",
                            colorText = R.color.red)) // Обновляем текст даты об ошибке и меняем цвет
                    }
                    is Resource.Loading -> {
                    }
                }
                delay(30000) //повторяем каждые 30 секунд
            }
        }
    }
}