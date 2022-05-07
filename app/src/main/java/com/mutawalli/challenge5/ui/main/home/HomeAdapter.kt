package com.mutawalli.challenge5.ui.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mutawalli.challenge5.R
import com.mutawalli.challenge5.data.api.Movies
import com.mutawalli.challenge5.databinding.ListItemMovieBinding
import com.mutawalli.challenge5.model.urlImage

class HomeAdapter : ListAdapter<Movies, HomeAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemMovieBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(dataPopular: Movies) {
            binding.apply {
                binding.tvTitleMovie.text = dataPopular.title
                binding.tvDateMovie.text = "Release: " + dataPopular.releaseDate
                binding.tvRatingMovie.text = "Popularity: " + dataPopular.popularity.toString()
                Glide.with(binding.root).load(urlImage + dataPopular.posterPath)
                    .error(R.drawable.ic_error)
                    .into(binding.ivImageMovie)
                root.setOnClickListener {
                    val id =
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(dataPopular.id!!)
                    it.findNavController().navigate(id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Movies>() {
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }

}