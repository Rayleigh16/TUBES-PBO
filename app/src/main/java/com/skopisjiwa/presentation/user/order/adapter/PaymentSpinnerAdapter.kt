package com.skopisjiwa.presentation.user.order.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.skopisjiwa.R
import com.skopisjiwa.data.order.PaymentSpinner

class PaymentSpinnerAdapter(
    context: Context,
    paymentList: ArrayList<PaymentSpinner>
) : ArrayAdapter<PaymentSpinner>(context, 0, paymentList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView,parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_spinner_payment, parent,false)
        }

        val imageViewFlag = itemView?.findViewById<ImageView>(R.id.image_view_flag)
        val textViewName = itemView?.findViewById<TextView>(R.id.text_view_name)

        val currentItem = getItem(position)

        if (currentItem != null) {
            imageViewFlag?.setImageResource(currentItem.logoPayment)
            textViewName?.text = currentItem.paymentName
        }

        return itemView!!
    }
}
