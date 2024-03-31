package su.afk.coursecurrencypsbank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import su.afk.coursecurrencypsbank.databinding.CurrencyItemBinding
import su.afk.coursecurrencypsbank.retrofit.Currency

class CurrencyAdapter(private var currencies: List<Currency>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    /* Метод onCreateViewHolder() вызывается для создания нового элемента списка.
    * Здесь мы создаем новый объект View, используя макет currency_item,
    * который содержит макет элемента списка, а затем
    * создаем и возвращаем новый объект CurrencyViewHolder.
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = CurrencyItemBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding.root)
    }

    /*
    * Метод onBindViewHolder() вызывается для привязки данных к представлению элемента списка в указанной позиции.
    * Здесь мы передаем объект Currency из списка currencies по заданной позиции и передаем его в
    * метод bind() класса CurrencyViewHolder, который обновляет данные элемента списка.
    * */
    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    /*
    * Метод getItemCount() возвращает общее количество элементов в списке.
    * В данном случае он возвращает размер списка currencies.
    * */
    override fun getItemCount(): Int {
        return currencies.size
    }

    /*
    * Заполняет данными каждый переданный view
     */
    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CurrencyItemBinding.bind(itemView)

        fun bind(currency: Currency) =with(binding) {
            tvCharCode.text = currency.CharCode
            tvName.text = currency.Name
            tvValue.text = currency.Value.toString()
        }
    }

    /*  Функция для обновления списка данных
    *  Уведомляет адаптер о том, что данные изменились, и RecyclerView должен обновить свое содержимое.
    * */
    fun updateData(newCurrencies: List<Currency>) {
        currencies = newCurrencies
        notifyDataSetChanged()
    }

}