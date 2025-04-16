package com.shadi777.currency.rate.tracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadi777.currency.rate.tracker.R
import com.shadi777.currency.rate.tracker.databinding.ItemCurrencyRateBinding
import com.shadi777.currency.rate.tracker.presentation.viewmodel.CurrencyDisplayData

class CurrencyRateDataAdapter(
    private val onItemClick: (String) -> Unit // Передаем callback
) : RecyclerView.Adapter<CurrencyRateDataAdapter.CurrencyRateViewHolder>() {

    fun submitData(data: MutableList<CurrencyDisplayData>) = differ.submitList(data)

    private val diffCallBack = object : DiffUtil.ItemCallback<CurrencyDisplayData>() {
        override fun areItemsTheSame(
            oldItem: CurrencyDisplayData,
            newItem: CurrencyDisplayData
        ): Boolean {
            return oldItem.shortCode == newItem.shortCode
        }

        override fun areContentsTheSame(
            oldItem: CurrencyDisplayData,
            newItem: CurrencyDisplayData
        ): Boolean {
            return oldItem.shortCode == newItem.shortCode &&
                    oldItem.fullName == newItem.fullName &&
                    oldItem.rate == newItem.rate &&
                    oldItem.iconUrl == newItem.iconUrl
        }

    }
    private val differ = AsyncListDiffer(this, diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemCurrencyRateBinding.inflate(layoutInflater, parent, false)
        return CurrencyRateViewHolder(itemBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class CurrencyRateViewHolder(
        private val dataBinding: ItemCurrencyRateBinding,
        private val onItemClick: (String) -> Unit,
    ) : RecyclerView.ViewHolder(dataBinding.root) {

        fun bindData(data: CurrencyDisplayData?) {
            dataBinding.currencyFullLabel.text = data?.fullName
            dataBinding.currencyShortLabel.text = data?.shortCode
            dataBinding.currencyValue.text = data?.rate.toString()

            Glide.with(this.itemView.context)
                .load(data?.iconUrl)
                .placeholder(R.drawable.ic_placeholder_currency)
                .error(R.drawable.ic_placeholder_currency)
                .into(dataBinding.currencyIcon)

            itemView.setOnClickListener {
                data?.shortCode?.let { onItemClick(it) }
            }
        }
    }
}
