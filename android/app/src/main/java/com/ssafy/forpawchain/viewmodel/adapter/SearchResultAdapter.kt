package com.ssafy.forpawchain.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.databinding.ControllSearchResultListBinding
import com.ssafy.forpawchain.model.domain.SearchResultDTO

class SearchResultViewHolder(
    private val binding: ControllSearchResultListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(searchResult: SearchResultDTO) {
        binding.item = searchResult
    }
}

class SearchResultAdapter(
    private val searchResultList: List<SearchResultDTO>
) : RecyclerView.Adapter<SearchResultViewHolder>() {

    private lateinit var binding: ControllSearchResultListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        binding = ControllSearchResultListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val searchlist = searchResultList[position]
        holder.bind(searchlist)
    }

    override fun getItemCount(): Int = searchResultList.size

}
