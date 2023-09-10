package com.sri.nasaimageoftheday.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.gson.Gson
import com.sri.nasaimageoftheday.R
import com.sri.nasaimageoftheday.databinding.ImageItemLayoutBinding
import com.sri.nasaimageoftheday.models.NasaImageItemData
import com.sri.nasaimageoftheday.ui.DetailsActivity


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
                //set video image and on click listener to open video link
                binding.nasaImageView.setImageResource(R.drawable.ic_video)
                binding.nasaImageView.scaleType=ImageView.ScaleType.FIT_CENTER
            }
            binding.imageRowLayout.setOnClickListener {
                val intent = Intent(it.context, DetailsActivity::class.java)
                intent.putExtra("item_json", Gson().toJson(result));
                it.context.startActivity(intent)
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
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(newData: ArrayList<NasaImageItemData>) {
        recipes = newData
        this.notifyDataSetChanged()
    }
}