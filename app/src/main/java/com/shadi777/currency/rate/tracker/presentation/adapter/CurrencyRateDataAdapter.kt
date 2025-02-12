package com.shadi777.currency.rate.tracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shadi777.currency.rate.tracker.databinding.ItemCurrencyRateBinding
import com.shadi777.currency.rate.tracker.domain.entity.CurrencyRateEntity

class CurrencyRateDataAdapter :
    RecyclerView.Adapter<CurrencyRateDataAdapter.CurrencyRateViewHolder>() {

    fun submitData(data: MutableList<CurrencyRateEntity>) = differ.submitList(data)

    private val diffCallBack = object : DiffUtil.ItemCallback<CurrencyRateEntity>() {
        override fun areItemsTheSame(
            oldItem: CurrencyRateEntity,
            newItem: CurrencyRateEntity
        ): Boolean {
            return oldItem.shortLabel == newItem.shortLabel
        }

        override fun areContentsTheSame(
            oldItem: CurrencyRateEntity,
            newItem: CurrencyRateEntity
        ): Boolean {
            return oldItem.shortLabel == newItem.shortLabel &&
                    oldItem.fullLabel == newItem.fullLabel &&
                    oldItem.rate == newItem.rate
        }

    }
    private val differ = AsyncListDiffer(this, diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemCurrencyRateBinding.inflate(layoutInflater, parent, false)
        return CurrencyRateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class CurrencyRateViewHolder(private val dataBinding: ItemCurrencyRateBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun bindData(data: CurrencyRateEntity?) {
            dataBinding.currencyFullLabel.text = data?.fullLabel
            dataBinding.currencyShortLabel.text = data?.shortLabel.toString()
            dataBinding.currencyValue.text = data?.rate.toString()
        }
    }
}
