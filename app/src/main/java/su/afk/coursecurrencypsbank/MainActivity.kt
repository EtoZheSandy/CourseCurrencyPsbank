package su.afk.coursecurrencypsbank

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import su.afk.coursecurrencypsbank.databinding.ActivityMainBinding
import su.afk.coursecurrencypsbank.screen.CurrencyAdapter
import su.afk.coursecurrencypsbank.screen.CurrencyViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: CurrencyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        val viewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        // Создаем адаптер и устанавливаем его для RecyclerView
        val currencyAdapter = CurrencyAdapter(emptyList())
        binding.RecyclerView.adapter = currencyAdapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        // Наблюдаем за списком валют
        viewModel.currencies.observe(this, Observer { currencies ->
            // Обновляем данные в адаптере
            currencyAdapter.updateData(currencies)
        })

        // Наблюдаем за текстом для обновления даты
        viewModel.updateDateText.observe(this, Observer { updateDateText ->
            // Обновляем текст в TextView
            binding.tvDateUpdate.text = updateDateText
        })

        // Для обновления состояния ProgressBar во время загрузки данных или его скрытия после завершения загрузки
        viewModel.isLoading.observe(this, Observer { status ->
            binding.progressBar.visibility = if (status == true) View.VISIBLE else View.GONE
        })

        // Наблюдаем за цветом текста даты в активити
        viewModel.textColor.observe(this, Observer { colorResId ->
            // Получаем цвет из ресурсов
            val textColor = ContextCompat.getColor(this, colorResId)
            // Устанавливаем цвет текста в TextView
            binding.tvDateUpdate.setTextColor(textColor)

            //TODO: Добавить кнопку повторить попытку и вызов loadData
        })
    }
}


