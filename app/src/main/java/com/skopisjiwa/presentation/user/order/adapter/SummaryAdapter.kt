package com.skopisjiwa.presentation.user.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skopisjiwa.R
import com.skopisjiwa.data.product.OrderSummaryModel
import com.skopisjiwa.utils.ConvertStringPrice

class SummaryAdapter(
    private val listSummaryOrder: ArrayList<OrderSummaryModel>
) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val numberSummary: TextView = itemView.findViewById(R.id.tv_number_produk_summary)
        val nameSummary: TextView = itemView.findViewById(R.id.tv_name_produk_summary)
        val hargaSummary: TextView = itemView.findViewById(R.id.tv_total_summary)
        val sizeProdukDrink: TextView = itemView.findViewById(R.id.tv_type_drink)
        val priceSizeProduk: TextView = itemView.findViewById(R.id.tv_price_type)
        val availableDrink: TextView = itemView.findViewById(R.id.tv_available_summary)
        val sugarLevelDrink: TextView = itemView.findViewById(R.id.tv_sugar_level_summary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_summary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryAdapter.ViewHolder, position: Int) {
        val summaryDrinkProduct: OrderSummaryModel = listSummaryOrder[position]
        holder.numberSummary.text = "${holder.adapterPosition + 1}"
        val hargaSumConvert =
            ConvertStringPrice.convertToCurrencyFormat(summaryDrinkProduct.hargaProduk.toInt())
        holder.hargaSummary.text = hargaSumConvert
        holder.nameSummary.text = summaryDrinkProduct.nameProduk
        holder.sizeProdukDrink.text = summaryDrinkProduct.sizeProduk
        holder.availableDrink.text = summaryDrinkProduct.availableProduk
        holder.sugarLevelDrink.text = summaryDrinkProduct.sugarLevelDrink
        val sizeHarga =
            summaryDrinkProduct.tambahanHargaSize?.let {
                ConvertStringPrice.convertToCurrencyFormat(
                    it.toInt())
            }
        holder.priceSizeProduk.text = sizeHarga
    }

    override fun getItemCount(): Int {
       return listSummaryOrder.size
    }
}