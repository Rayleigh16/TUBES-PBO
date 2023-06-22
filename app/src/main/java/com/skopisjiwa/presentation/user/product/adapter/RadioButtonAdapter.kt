package com.skopisjiwa.presentation.user.product.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skopisjiwa.R
import com.skopisjiwa.data.product.OptionRadioButton
import com.skopisjiwa.utils.ConvertStringPrice

class RadioButtonAdapter(
    private val list: ArrayList<OptionRadioButton>
) : RecyclerView.Adapter<RadioButtonAdapter.ViewHolder>() {


    private var selectedPosition = -1


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameOption: TextView = view.findViewById(R.id.tv_name_option)
        val radioButton: RadioButton = view.findViewById(R.id.radioButton)
        val priceAdd: TextView = view.findViewById(R.id.tv_price_radio)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RadioButtonAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_radio_button, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RadioButtonAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val radioBtn: OptionRadioButton = list[position]

        val convertPrice = ConvertStringPrice.convertToCurrencyFormat(radioBtn.price)
        holder.nameOption.text = radioBtn.size
        holder.priceAdd.text = convertPrice

        holder.radioButton.isChecked = position == selectedPosition

        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun getSelectedPosition(): Int {
        return selectedPosition
    }
}