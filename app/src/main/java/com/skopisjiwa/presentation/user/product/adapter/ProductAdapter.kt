package com.skopisjiwa.presentation.user.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skopisjiwa.R
import com.skopisjiwa.data.product.ProductModel
import com.skopisjiwa.utils.ConvertStringPrice

class ProductAdapter(
    private val listProduct: ArrayList<ProductModel>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduk: ImageView = itemView.findViewById(R.id.img_produk)
        val nameProduk: TextView = itemView.findViewById(R.id.tv_name_produk)
        val hargaProduk: TextView = itemView.findViewById(R.id.tv_harga)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk: ProductModel = listProduct[position]
        holder.imageProduk.setImageResource(produk.gambarProduk)
        holder.nameProduk.text = produk.nameProduk

        val formatPrice = ConvertStringPrice.convertToCurrencyFormat(produk.hargaProduk)
        holder.hargaProduk.text = formatPrice

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(produk)
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }



    interface CellClickListener {
        fun onCellClickListener(data: ProductModel)
    }

}