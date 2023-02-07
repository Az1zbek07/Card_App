package com.example.cardapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapp.databinding.ItemLayoutBinding
import com.example.cardapp.model.CardResponse

class CardAdapter: ListAdapter<CardResponse, CardAdapter.VHolder>(DiffCallback()) {
    lateinit var onClick: (CardResponse) -> Unit
    lateinit var onDelete: (id: Int) -> Unit
    private class DiffCallback: DiffUtil.ItemCallback<CardResponse>(){
        override fun areItemsTheSame(oldItem: CardResponse, newItem: CardResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CardResponse, newItem: CardResponse): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cardResponse: CardResponse){
            binding.textHolderName.text = cardResponse.cardHolderName
            binding.textBankName.text = cardResponse.bankName
            binding.textNumber.text = cardResponse.number.toString()
            binding.textDate1.text = cardResponse.date1.toString()
            binding.textDate2.text = cardResponse.date2.toString()
            binding.btnDelete.setOnClickListener {
                onDelete(cardResponse.id)
            }
            itemView.setOnClickListener {
                onClick(cardResponse)
            }
        }
    }
}