package com.akhilesh.newsapp.ui.sources

import androidx.recyclerview.widget.RecyclerView
import com.akhilesh.newsapp.data.local.entity.NewsSource
import com.akhilesh.newsapp.databinding.NewsSourcesItemLayoutBinding
import com.akhilesh.newsapp.utils.ItemClickListener


class NewsSourcesListViewHolder(private val binding: NewsSourcesItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(newsSource: NewsSource, itemClickListener: ItemClickListener<NewsSource>) {
        binding.newsSourceBtn.text = newsSource.name
        itemView.setOnClickListener {
            itemClickListener(bindingAdapterPosition, newsSource)

        }
    }


}
