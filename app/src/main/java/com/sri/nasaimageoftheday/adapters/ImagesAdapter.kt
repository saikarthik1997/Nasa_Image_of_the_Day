package com.sri.nasaimageoftheday.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sri.nasaimageoftheday.R
import com.sri.nasaimageoftheday.databinding.ImageItemLayoutBinding
import com.sri.nasaimageoftheday.models.NasaImageItemData
import com.sri.nasaimageoftheday.models.NasaImageResponseData
import com.sri.nasaimageoftheday.utils.NetworkResult

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {
    private var recipes = emptyList<NasaImageItemData>()

    class MyViewHolder(private val binding: ImageItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: NasaImageItemData) {
            binding.imageItem = result
            if (result.mediaType == "image"){
                //set image url
                binding.nasaImageView.load(result.url) {
                    crossfade(600)
                    placeholder(R.drawable.baseline_image_24)
                }
            }else{
                //set video image
                binding.nasaImageView.setImageResource(R.drawable.ic_video)
                binding.nasaImageView.scaleType=ImageView.ScaleType.FIT_CENTER
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ImageItemLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: NasaImageResponseData) {
        recipes = newData
        this.notifyDataSetChanged()
    }
}