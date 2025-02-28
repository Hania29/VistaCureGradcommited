package com.example.vistacuregrad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vistacuregrad.Newactivity.Model.VcareItem

class VcareAdapter(private val itemList: List<VcareItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_DISCLAIMER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            itemList[position].isHeader -> TYPE_HEADER
            itemList[position].isDisclaimer -> TYPE_DISCLAIMER  // Check properly if it's a disclaimer
            else -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false))
            TYPE_DISCLAIMER -> DisclaimerViewHolder(inflater.inflate(R.layout.item_disclaimer, parent, false))
            else -> ItemViewHolder(inflater.inflate(R.layout.item_vcare, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        when (holder) {
            is HeaderViewHolder -> holder.title.text = item.title
            is ItemViewHolder -> {
                holder.title.text = item.title
                holder.description.text = item.description
            }
            is DisclaimerViewHolder -> {
                holder.disclaimerTitle.text = "Disclaimer!"  // Set fixed title
                holder.disclaimerText.text = item.description // Set dynamic description
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvHeaderTitle)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
    }

    class DisclaimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val disclaimerTitle: TextView = itemView.findViewById(R.id.tvDisclaimerTitle) // Add title binding
        val disclaimerText: TextView = itemView.findViewById(R.id.tvDisclaimerText) // Bind correctly
    }
}
