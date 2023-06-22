package com.skopisjiwa.presentation.user.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skopisjiwa.R
import com.skopisjiwa.data.store.StoreModel
import com.squareup.picasso.Picasso

class StoreAdapter(
    listStore: MutableList<StoreModel>,
    private val cellClickListener: CellClickListener,
    private val context: Context,
) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    private var holder1: ViewHolder? = null
    private var roldeId: Long = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleStore: TextView = itemView.findViewById(R.id.tv_title_store)
        val imageStore: ImageView = itemView.findViewById(R.id.img_store)
        val openStore: TextView = itemView.findViewById(R.id.tv_time_operation)
        val addresStore: TextView = itemView.findViewById(R.id.tv_address)
        val btnChoose: Button = itemView.findViewById(R.id.btn_choose_store)
    }

    private var listStore: MutableList<StoreModel>

    init {
        this.listStore = listStore
    }

    fun setDataStore(data: List<StoreModel>) {
        this.listStore.clear()
        this.listStore.addAll(data)
        notifyDataSetChanged()
    }

    fun filter(filteredList: MutableList<StoreModel>) {
        this.listStore = filteredList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_store, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listStore.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store: StoreModel = listStore[position]

        Picasso.get().load(store.image).into(holder.imageStore)
//        holder.imageStore.setImageResource(store.image)
        holder.titleStore.text = store.titleStore
        holder.openStore.text = store.open
        holder.addresStore.text = store.address

        holder.btnChoose.setOnClickListener {
            cellClickListener.onCellClickListener(store)
        }

        if (roldeId == 1L) {
            holder.btnChoose.text = "Edit Store"
        }
    }


    fun getIdRole(idHolder: Long) {
        roldeId = idHolder
    }

    interface CellClickListener {
        fun onCellClickListener(data: StoreModel)
    }

}