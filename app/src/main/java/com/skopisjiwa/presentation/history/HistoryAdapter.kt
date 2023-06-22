package com.skopisjiwa.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skopisjiwa.data.bill.HistoryTransactionModel
import com.skopisjiwa.databinding.ItemHistoryBinding
import com.skopisjiwa.utils.ConvertStringPrice

class HistoryAdapter(
    private val listOrderHistory: ArrayList<HistoryTransactionModel>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textNo = binding.tvNumber
        val numberOrder = binding.tvResult
        val status = binding.containerStatus.tvResult
        val order = binding.containerOrder.tvResult
        val total = binding.containerTotal.tvResult
        val grandTotal = binding.containerGrandTotal.tvResult
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemHistoryBinding = ItemHistoryBinding.inflate(inflater, parent, false)

        binding.containerOrder.tvTitle.text = "Order"
        binding.containerStatus.tvTitle.text = "Status"
        binding.containerTotal.tvTitle.text = "Total Bayar"
        binding.containerGrandTotal.tvTitle.text = "Grand Total"

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val historyTransaction: HistoryTransactionModel = listOrderHistory[position]

        holder.grandTotal.text =
            ConvertStringPrice.convertToCurrencyFormat(historyTransaction.grandPrice!!.toInt())
        holder.status.text = historyTransaction.status
        val textNo = position + 1 // Increment nomor sesuai dengan posisi
        holder.textNo.text = textNo.toString()
        holder.numberOrder.text = historyTransaction.number?.substring(0, 4)
        holder.total.text =
            ConvertStringPrice.convertToCurrencyFormat(historyTransaction.totalPrice!!.toInt())
        holder.order.text = historyTransaction.typeOrder
    }

    override fun getItemCount(): Int {
        return listOrderHistory.size
    }
}