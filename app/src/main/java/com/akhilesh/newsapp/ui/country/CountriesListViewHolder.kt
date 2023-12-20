package com.akhilesh.newsapp.ui.country

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.akhilesh.newsapp.R
import com.akhilesh.newsapp.data.local.entity.Country
import com.akhilesh.newsapp.databinding.NewsSourcesItemLayoutBinding
import com.akhilesh.newsapp.utils.ItemClickListener


class CountriesListViewHolder(private val binding: NewsSourcesItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(country: Country, itemClickListener: ItemClickListener<Country>) {
        binding.newsSourceBtn.text = country.name
        binding.newsSourceBtn.setBackgroundColor(
            ContextCompat.getColor(
                binding.newsSourceBtn.context, R.color.orange
            )
        )
        itemView.setOnClickListener {
            itemClickListener(bindingAdapterPosition, country)
        }
    }
}
