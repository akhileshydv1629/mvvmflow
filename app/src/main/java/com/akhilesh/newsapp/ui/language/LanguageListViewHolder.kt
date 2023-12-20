package com.akhilesh.newsapp.ui.language

import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.akhilesh.newsapp.R
import com.akhilesh.newsapp.data.model.Language
import com.akhilesh.newsapp.databinding.LanguageItemLayoutBinding


class LanguageListViewHolder(private val binding: LanguageItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(
        language: Language, isActivated: Boolean = false
    ) {
        binding.newsSourceBtn.text = language.name
        binding.newsSourceBtn.setBackgroundColor(
            ContextCompat.getColor(
                binding.newsSourceBtn.context, R.color.pink
            )
        )
        itemView.isActivated = isActivated
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = bindingAdapterPosition

            override fun getSelectionKey(): Long = itemId
        }
}
